package com.eshilov.auth.common.token;

import static com.eshilov.auth.token.model.TokenType.ACCESS;
import static com.eshilov.auth.token.model.TokenType.REFRESH;
import static com.eshilov.auth.utils.DateUtils.convertLocalDateTimeToDate;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.springframework.http.HttpStatus.OK;

import com.eshilov.auth.AppProperties;
import com.eshilov.auth.generated.model.TokenPair;
import com.eshilov.auth.token.model.TokenType;
import java.time.LocalDateTime;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.SoftAssertions;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenPairResponseEntityAssertion {

    private static final int MAX_SECS_PASSED_SINCE_TOKEN_CREATION = 30;

    private final TestTokenHelper testTokenHelper;
    private final AppProperties appProperties;

    public void execute(ResponseEntity<TokenPair> responseEntity, String subject) {
        assertSoftly(
                softly -> {
                    var statusCode = responseEntity.getStatusCode();
                    softly.assertThat(statusCode).isEqualTo(OK);

                    var body = responseEntity.getBody();
                    softly.assertThat(body).isNotNull();

                    assertToken(
                            softly,
                            body.getAccess(),
                            subject,
                            ACCESS,
                            appProperties::getAccessTokenValiditySecs);

                    assertToken(
                            softly,
                            body.getRefresh(),
                            subject,
                            REFRESH,
                            appProperties::getRefreshTokenValiditySecs);
                });
    }

    private void assertToken(
            SoftAssertions softly,
            String token,
            String subject,
            TokenType type,
            Supplier<Long> validitySecsSupplier) {

        var tokenData = testTokenHelper.parseToken(token);

        var minExpirationLocalDateTime =
                LocalDateTime.now()
                        .plusSeconds(validitySecsSupplier.get())
                        .minusSeconds(MAX_SECS_PASSED_SINCE_TOKEN_CREATION);
        var minExpiration = convertLocalDateTimeToDate(minExpirationLocalDateTime);

        softly.assertThat(tokenData.getSubject()).isEqualTo(subject);
        softly.assertThat(tokenData.getExpiration()).isAfter(minExpiration);
        softly.assertThat(tokenData.getType()).isEqualTo(type);
    }
}
