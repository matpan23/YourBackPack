<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="javax.swing.*" %>
<%@ page import="Model.Bean.*" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="CSS/styles.css">
    <link rel="stylesheet" href="CSS/visPref.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0" />
    <title>YourBackPack</title>
    <script src="JavaScript/jquery-3.7.0.js"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=DM+Sans:ital,wght@1,700&family=Rubik:ital,wght@1,300&display=swap" rel="stylesheet">
    <script src="https://cdn.lordicon.com/bhenfmcm.js"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=DM+Sans:ital,wght@1,700&family=Rubik:ital,wght@1,300&display=swap" rel="stylesheet">
</head>
<body>
<%
    Carrello cart = (Carrello) request.getSession(false).getAttribute("cart");
    int nprod;
    if(cart == null){
        nprod = 0;
    }
    else{
        nprod = cart.getnZaini();
    }
    int mode = (int) request.getSession(false).getAttribute("mode");
    DecimalFormat df = new DecimalFormat("#.00");
%>
<header class="header">
    <div class="HeaderfirstDiv">
        <div class="SonFirstDiv">
            <div class="homeElement">
                <a class="logoc" href="MostraZainiServlet">
                    <img class="imgOptions" src="CSS/LogoOff.png">
                </a>
            </div>
        </div>
        
        <form class="formSearch" action="search-servlet">
            <input class="searchpanel" type="text" name="search" placeholder="Cerca">
        </form>
        
        <div class="HeaderQuick">
            <%
                if(mode == 2){%>
            <a style="text-decoration: none" href="profilo.jsp"><button class="forButton">PROFILO</button></a>
            <%}else if(mode == 1) {%><a style="text-decoration: none" href="admin.jsp"><button class="forButton">ADMIN</button></a>
            <%} else{%><a style="text-decoration: none" href="Login.jsp"><button class="forButton">LOG-IN</button></a><%}%>
            <a style="text-decoration: none" href="MostraCarrelloServlet"><button class="forButton">CARRELLO(<span id="num_prod"><%=nprod%></span>)</button></a>
        </div>
        <div class="HeaderQuick2">
            <div><a href="MostraCarrelloServlet"><img src="CSS/ShopBag2.svg"></a></div>
            <%
                if(mode == 2){%>
            <div><a href="profilo.jsp"><img src="CSS/Account.svg"></a></div>
            <%}else if(mode == 1) {%><div><a href="admin.jsp"><img src="CSS/Account.svg"></a></div>
            <%} else{%><div><a href="Login.jsp"><img src="CSS/Account.svg"></a></div><%}%>
        </div>
        <div class="hamburger" id="openBr">
            <div class="HambIcon">          <!-- come se fosse <i> -->
                <a><img src="CSS/134216_menu_lines_hamburger_icon.svg"></a>
            </div>
        </div>
        <div id="mobileMenu" class="mobileMenu">
            <i id="closeMenu" class="closeIcon">X</i>
            <div class="HeaderQuick3">
                <div>
                    <a href="MostraCarrelloServlet"><img style="width: 35px; height: 35px" src="CSS/ShopBag2.svg"></a>
                    <a href="MostraCarrelloServlet">Carrello(<span id="num_prod2"><%=nprod%></span>)</a>
                </div>
                <div>
                    <%
                        if(mode == 2){%>
                    <a style="text-decoration: none" href="profilo.jsp"><img style="width: 35px; height:  35px;" src="CSS/Account.svg"></a>
                    <a href="profilo.jsp">Profilo</a>
                    <%}else if(mode == 1) {%><a style="text-decoration: none" href="admin.jsp"><img style="width: 35px; height:  35px;" src="CSS/Account.svg"></a>
                    <a href="admin.jsp">Admin</a>
                    <%} else{%><a style="text-decoration: none" href="Login.jsp"><img style="width: 35px; height:  35px;" src="CSS/Account.svg"></a>
                    <a href="Login.jsp">Log-In/Sign-in</a><%}%>
                </div>
            </div>
            <div class="bluBan"></div>
        </div>
    </div>
</header>
<% List<Zaino> lstZaino = (List<Zaino>) request.getAttribute("Zaini");
   List<Contenere> lstContenere = (List<Contenere>) request.getSession().getAttribute("contenere");
%> 
<div style="color: red" id="emptycart"></div>
<%if(cart.getnZaini() == 0) {%>
<div style="color: red" id="emptycart">Carrello vuoto</div>
<%}%>
<div class="zainiPref">
    <%  if(lstZaino != null && !lstZaino.isEmpty()) {
        for (Zaino z : lstZaino){
            for(Contenere c : lstContenere) {
                if(z != null) {
                    if (c.getZaino().equals(z.getCodice())) {
    %>
        <div class="zainoPref">
            <div class="ImageContainerPref">
                <a href="ZainiServlet?codice=<%=z.getCodice()%>" style="text-decoration: none"><img src="ZainoIMG/<%="img"+ z.getCodice() + ".jpg"%>"></a>
            </div>
            <div class="infoAndButton">
                <div class="TitleBookPref">
                    <a href="ZainiServlet?codice=<%=z.getCodice()%>" style="text-decoration: none"><p><%= z.getTitolo() %></p></a>
                </div>
                <div class="pricePref">
                    <p id="<%=z.getCodice()%>"><%="Copie :" + c.getNumQuantita() + " "%>
                    <p style="color: red"><%
                    String errore = (String) request.getAttribute("errore");
                    if(errore != null) {
                        String[] array = errore.split("-");
                        if (array[0].equals("Quantità non disponibile") && array[1].equals(z.getCodice())) {%>
                    Diminuire le quantità di questo libro<%}}%></p>
                </div>
                <div class="BookButtPref">
                    <button class="add" value= "<%=z.getCodice()%>">Aggiungi Zaino</button>
                    <button class="rem" value= "<%=z.getCodice()%>" >Rimuovi Zaino</button>
                </div>
            </div>
        </div>
    <%
                        break;
                    }
                }
            }
        }
    }
    if(cart.getnZaini() != 0){ //faccio la chiamata ad acquisto servlet%>
    <div class="acquista" id="acquista">
        <div><p id="totale">Totale: <%=cart.getTotale()%>€</p></div>
        <div><a href="AcquistoServlet" style="text-decoration: none"><button>Acquista ora</button></a></div>
    </div>
    <%}%>
</div>
</body>
<script>
    $(".add").click(function addCart() {
        let codice = $(this).val();
        const xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.status == 200 && this.readyState == 4) {
                let s = this.responseText;
                if(s === "-2"){
                    alert("Quantità non disponibile")
                }
                else {
                    const array = s.split("-");
                    let totale = parseFloat(array[2]);
                    totale.toFixed(2);
                    document.getElementById("num_prod").innerHTML = array[0];
                    document.getElementById("num_prod2").innerHTML = array[0];
                    document.getElementById(codice).innerHTML = "Copie: " + array[1];
                    document.getElementById("totale").innerHTML = "Totale: " + totale + "€";
                }
            }
        }
        xhttp.open("POST", "carrelloservlet");
        xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhttp.send("codice=" + codice);
    })

    $(".rem").click(function remCart() {
        let codice = $(this).val();
        let element = $(this);
        const xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.status == 200 && this.readyState == 4) {
                let s = this.responseText;
                const array = s.split("-");
                if(array[0] === "0" && array[1] === "0") {//controllo la quantità del carrello = 0 e il numero di copie di quel libro = 0
                    element.parent().parent().parent().hide();
                    document.getElementById("num_prod").innerHTML = array[0];
                    document.getElementById("num_prod2").innerHTML = array[0];//scrivono 0
                    document.getElementById("emptycart").innerHTML = "Carrello vuoto";//scrive il carrello è vuoto
                    $("#acquista").hide();//nasconde il div di acquisto
                }
                else if(array[0] === "0"){
                    element.parent().parent().parent().hide();
                    document.getElementById("num_prod").innerHTML = array[0];
                    document.getElementById("num_prod2").innerHTML = array[0];
                    document.getElementById("emptycart").innerHTML = "Carrello vuoto";
                    $("#acquista").hide();
                }
                else if(array[1] === "0"){//controlla se ho rimosso l'ultima copia di quel libro
                    let totale = parseFloat(array[2]);//prende il totale del carrello
                    element.parent().parent().parent().hide();
                    document.getElementById("num_prod").innerHTML = array[0];
                    document.getElementById("num_prod2").innerHTML = array[0];
                    document.getElementById("totale").innerHTML = "Totale: " + totale + "€";
                }
                else{
                    let totale = parseFloat(array[2]);
                    document.getElementById("num_prod").innerHTML = array[0];
                    document.getElementById("num_prod2").innerHTML = array[0];
                    document.getElementById(codice).innerHTML = "Copie: " + array[1];
                    document.getElementById("totale").innerHTML = "Totale: " + totale + "€";
                }
            }
        }
        xhttp.open("POST", "RimuoviCarrelloServlet");
        xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhttp.send("codice=" + codice);
    })
</script>
<script>
    let hamburger = document.querySelector(".HambIcon");
    let closeIcon = document.getElementById("closeMenu");
    let mobileMenu = document.getElementById("mobileMenu");

    hamburger.addEventListener("click", function() {
        mobileMenu.style.transform = 'translateX(0)'; // sposta il menu a destra
    });

    closeIcon.addEventListener("click", function() {
        mobileMenu.style.transform = 'translateX(-100%)'; // sposta il menu a sinistra
    });
</script>
<script>
    window.addEventListener('resize', function() {
        if (window.innerWidth > 1180) {
            mobileMenu.style.transform = 'translateX(-100%)'; // sposta il menu a sinistra
        }
    });
</script>
</html>
