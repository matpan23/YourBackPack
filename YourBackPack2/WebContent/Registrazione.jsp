<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registrazione</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="CSS/SignIn.css">
    <!-- Font -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=DM+Sans:ital,wght@1,700&family=Rubik:ital,wght@1,300&display=swap" rel="stylesheet">
</head>
<body>
<body>
<div class="UpperContainer">
    <div class="container">

        <form class="form signup" name="reg" method="post" action="registrazione-servlet">
            <h2>Registrazione</h2>
            <div class="inputBox">
                <input type="text" name="username" required="required">
                <ion-icon name="person-outline"></ion-icon>
                <span>Username</span>
            </div>
            <div class="inputBox">
                <input type="text" name="name" required="required">
                <ion-icon name="person-outline"></ion-icon>
                <span>Nome</span>
            </div>
            <div class="inputBox">
                <input type="text" name="surname" required="required">
                <ion-icon name="person-outline"></ion-icon>
                <span>Cognome</span>
            </div>
            <div class="inputBox">
                <input type="email" name="email" required="required" >
                <ion-icon name="mail-outline"></ion-icon>
                <span>Indirizzo E-Mail</span>
            </div>
            <div class="inputBox">
                <input type="password" id="password" name="password" required="required">
                <ion-icon name="lock-closed-outline"></ion-icon>
                <span>Inserisci Password</span>
            </div>
            <div class="inputBox">
                <input type="password" id="passwordrep" name="passwordrep" required="required" onblur="registration()">
                <ion-icon name="lock-closed-outline"></ion-icon>
                <span> Conferma Password</span>
            </div>
            <div class="inputBox">
                <input type="text" name="via" required="required">
                <ion-icon name="person-outline"></ion-icon>
                <span>Via</span>
            </div>
            <div class="inputBox">
                <input type="text" name="citta" required="required">
                <ion-icon name="person-outline"></ion-icon>
                <span>Città</span>
            </div>
            <div class="inputBox">
                <input type="text" name="CAP" required="required" maxlength="5" pattern="[0-9]{5}">
                <ion-icon name="person-outline"></ion-icon>
                <span>CAP</span>
            </div>
            <div class="inputBox">
                <input type="tel" name="telefono" required="required" pattern="[0-9]{10}">
                <ion-icon name="person-outline"></ion-icon>
                <span>Telefono</span>
            </div>
            <div class="inputBox">
                <input type="submit"  id="bottone" value="Create Account">
            </div>
            <p>Sei già registrato? <a style="text-decoration: none; color: royalblue" href="Login.jsp" class="login">Accesso</a></p>
            <label for="passwordrep" id="confirm" style="color:red; display: none">
                Le password non corrispondono
            </label>
            <p style="color: red">
                <%
                    String errore = (String) request.getAttribute("errore");
                    String[] strings;
                    String risposta = "";
                    if(errore != null) {
                        strings = errore.split(",");
                        for (String s : strings) {
                            if (s.equals("1")) {
                                risposta += "Utente già registrato<br>";
                            } else if (s.equals("2")) {
                                risposta += "Le password non corrispondono<br>";
                            } else if (s.equals("3")) {
                                risposta += "L'email non è corretta<br>";
                            } else if (s.equals("4")) {
                                risposta += "CAP non corretto<br>";
                            } else if (s.equals("5")) {
                                risposta += "Telefono non corretto<br>";
                            } else if (s.equals("6")) {
                                risposta += "Campo/i nullo/i<br>";
                            }
                        }
                    }
                    if(!risposta.equals("")){%>
                <%=risposta%>
                <%}%>
            </p>
        </form>
    </div>
</div>

<div class="returnDiv">
    <a class="noDec2" href="MostraZainiServlet"><button class="forButton2">Home</button></a>
</div>

</body>
<script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
<script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
<script>

    function registration(){
        let button = document.getElementById("bottone");
        let passrep = document.forms["reg"]["passwordrep"].value;
        let pass = document.forms["reg"]["password"].value;
        if( pass.localeCompare(passrep) !== 0) {
            document.getElementById("confirm").style.display="block";
            button.disabled = true;
        }
        else{
            document.getElementById("confirm").style.display="none";
            button.disabled = false;
        }
    }
</script>
</body>
</html>
