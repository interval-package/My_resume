<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="master.css">
</head>
<body>
<div id="login-window">
    <div id="login-box" class="glassy-dark-bg rounded-custom">
        <div id='logo'>
            <img src="images/dummy_logo.png" alt="logo" height="64" />
        </div>
        <h2 id="login-header" class="logo-font">Login</h2>
        <form class="my-3" action="/My_resume/login" method="get" id="login-form">
            <input class="input text-pale-light full-opacity-bg rounded-custom" name="account" id="account" autoComplete="username" placeholder="Account" /><br />
            <input class="input text-pale-light full-opacity-bg rounded-custom" type="password" name="password" id="password" autoComplete="current-password" placeholder="Password" /><br />
            <input type="hidden" name="init" value="true">
            <div class="remember-section">
                <input type="checkbox" name="remember" id="remember" />
                <label>Remember me</label>
            </div>
            <input class="submit-btn" type="submit" value="Submit" id="submit_btn"/>
        </form>
    </div>
</div>
</body>

<script src="./login.js"></script>
</html>