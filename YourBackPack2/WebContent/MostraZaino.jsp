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
    <link rel="stylesheet" href="CSS/visZaino.css">
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
<div class="Zaini">

    <% List<Zaino> zainos = (List<Zaino>) request.getAttribute("Zaini");
        List<String> titoli= new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#.00");
        if(zainos != null){
            for (Zaino c: zainos) {
                String codice = c.getCodice();
                String titolo = c.getTitolo();
                int prezzo = c.getPrezzo();

    %>
    <div class="Zaino">
        <div class="ImageContainer">
            <a href="ZainiServlet?codice=<%=c.getCodice()%>" style="text-decoration: none"><img src="ZainoIMG/<%="img"+ c.getCodice() + ".jpg"%>"></a>
        </div>
        <div class="TitleBook">
            <a href="ZainiServlet?codice=<%=c.getCodice()%>" style="text-decoration: none"> <p><%=titolo%></p></a>
        </div>
        <div class="price">
            <p><%=df.format(prezzo) + "€"%></p>
        </div>
        <div class="BookButt">
            <button class="add" value="<%=codice%>"><img style="width: 15px; height: 15px; color: white" src="CSS/ShopBag2.svg"></button>
        </div>
    </div>

    <%
            }}else {
    %>
    <div style="align-content: center; color: red; font-size: large">
        Nessuno zaino trovato.
    </div>
    <% } %>
</div>
<div class="finalInfo">
    <div class="Icone">
        <div>
            <img style="width: 30px; height: 30px" src="CSS/FaceBookImg.svg" alt="Checkk">
            <p>YourBackPack</p>
        </div>
        <div>
            <img style="width: 30px; height: 30px" src="CSS/InstaImg.svg" alt="Checkk">
            <p>@YourBackPack</p>
        </div>
        <div>
            <img style="width: 30px; height: 30px" src="CSS/Twitter.svg" alt="Checkk">
            <p>YourBackPackTW</p>
        </div>
    </div>

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
                    alert("Quantità non disponibile");
                }
                else {

                    const array = s.split("-");
                    document.getElementById("num_prod").innerHTML = array[0];
                    document.getElementById("num_prod2").innerHTML = array[0];


                }
            }
        }
        xhttp.open("POST", "carrelloservlet");
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
