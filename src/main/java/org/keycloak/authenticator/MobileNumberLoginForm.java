package org.keycloak.authenticator;


import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;

import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;

import org.keycloak.models.UserModel;
import org.keycloak.models.utils.FormMessage;
import org.keycloak.services.validation.Validation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.keycloak.representations.IDToken.PHONE_NUMBER;

public class MobileNumberLoginForm implements Authenticator {

    private static final String MOBILE_NUMBER_FIELD = "mobile_number";

    private static final String MOBILE_INPUT_LOGIN_FTL = "mobile-input-login.ftl";

    private static final String OTP_INPUT_LOGIN_FTL = "otp-input-login.ftl";


    @Override
    public void authenticate(AuthenticationFlowContext context) {

        System.out.println("authenticate ....!!!!!");

        context.challenge(context.form().createForm(MOBILE_INPUT_LOGIN_FTL));


    }

    @Override
    public void action(AuthenticationFlowContext context) {

        System.out.println("ACTION ....!!!!!");

        MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();

        String pageType = formData.getFirst("page_type");

        switch (pageType) {

            case "mobile_input_page":
                sendOtp(context);
                break;
            case "otp_input_page":
                validateOtp(context);
                break;
            default:
                break;

        }


    }

    private void sendOtp(AuthenticationFlowContext context) {

        MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();

        String phoneNumber = formData.getFirst("phone_number");

        context.getAuthenticationSession().setAuthNote("phoneNumber", phoneNumber);
        context.getAuthenticationSession().setAuthNote("otp", "123456");

        context.challenge(context.form().createForm(OTP_INPUT_LOGIN_FTL));


    }

    private void validateOtp(AuthenticationFlowContext context) {

        MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();

        String otpNumber = formData.getFirst("otp_number");

        String phoneNumber = context.getAuthenticationSession().getAuthNote("phoneNumber");

        if (otpNumber.equals("123456")) {
            UserModel user = context.getSession().users().searchForUserByUserAttributeStream(context.getRealm(), "phone_number", phoneNumber).findFirst().orElse(null);
            if (user != null) {
                context.setUser(user);
                context.success();
            }

        } else {
            List<FormMessage> errorMessage = new ArrayList<>();
            errorMessage.add(new FormMessage("notValidMessage", "OTP is not Valid"));
            context.failureChallenge(AuthenticationFlowError.INTERNAL_ERROR, context.form().setErrors(errorMessage).createForm(OTP_INPUT_LOGIN_FTL));
        }


    }

    @Override
    public boolean requiresUser() {
        return false;
    }

    @Override
    public boolean configuredFor(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {
        return false;
    }

    @Override
    public void setRequiredActions(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {

    }

    @Override
    public void close() {

    }
}
