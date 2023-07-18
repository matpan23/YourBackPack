<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Accesso</title>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="CSS/logPage.css">
</head>
<body>
<div class="UpperContainer">
    <div class="container">
        <form class="form signup" action="login-servlet" method="post">
            <h2>Accesso</h2>
            <div class="inputBox">
                <input type="text" name="email" required="required">
                <ion-icon name="mail-outline"></ion-icon>
                <span>Indirizzo E-Mail</span>
            </div>
            <div class="inputBox">
                <input type="password" id="password" name="password" required="required">
                <ion-icon name="lock-closed-outline"></ion-icon>
                <span>Inserisci Password</span>
            </div>
            <div class="inputBox">
                <input type="submit"  value="Accedi">
            </div>
            <p>Non sei ancora registrato? <a href="Registrazione.jsp" class="login" style="text-decoration: none; color: royalblue">Registrati</a></p>
            <p style="color: red">
                <%
                    String errore = (String) request.getAttribute("error");
                    String risposta = "";
                    if((errore != null) && errore.equals("campo incompleto")) {
                        risposta += "Campo/i nullo/i";
                    }
                    else if((errore != null) && errore.equals("utente not found")){
                        risposta += "Email o password errate";
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
</html>