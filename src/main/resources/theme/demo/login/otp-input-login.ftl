<!DOCTYPE html>
<html>
<head>
    <title>Login</title>

</head>
<body>
    <div id="kc-login">
        <div id="kc-content">
            <div class="form-container">

                <form id="kc-login-form" action="${url.loginAction}" method="post">

                <#if messagePerField?has_content && messagePerField.notValidMessage?size > 0>
                        <div class="alert alert-danger">
                            <ul>
                                <#list messagePerField.notValidMessage as message>
                                    <li>${message.message}</li>
                                </#list>
                            </ul>
                        </div>
                    </#if>

                <input type="hidden" name="page_type" value="otp_input_page">
                    <div class="form-group">
                        <label for="phone_number">OTP:</label>
                        <input type="text" id="otp_number" name="otp_number" class="form-control" placeholder="Enter your OTP" required>
                    </div>

                    <button type="submit" class="btn btn-primary">Login</button>

                </form>
            </div>
        </div>
    </div>
</body>
</html>
