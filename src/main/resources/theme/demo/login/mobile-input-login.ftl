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
                <input type="hidden" name="page_type" value="mobile_input_page">
                    <div class="form-group">
                        <label for="phone_number">Phone Number:</label>
                        <input type="text" id="phone_number" name="phone_number" class="form-control" placeholder="Enter your phone number" required>
                    </div>

                    <button type="submit" class="btn btn-primary">Send OTP</button>

                </form>
            </div>
        </div>
    </div>
</body>
</html>
