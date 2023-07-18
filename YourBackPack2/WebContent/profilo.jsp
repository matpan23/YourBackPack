<%@ page import="Model.Bean.Carrello" %>
<%@ page import="Model.Bean.Utente" %>
<%@ page import="Model.Bean.Ordine" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="CSS/styles.css">
    <link rel="stylesheet" href="CSS/Profilo.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0" />
    <title>YourBackPack</title>

    <script src="https://cdn.lordicon.com/bhenfmcm.js"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=DM+Sans:ital,wght@1,700&family=Rubik:ital,wght@1,300&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">


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
    int mode = (int) request.getSession().getAttribute("mode");
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
<%
    Utente utente = (Utente) request.getSession().getAttribute("utente");
    List<Ordine> ordini = (List<Ordine>) request.getSession().getAttribute("ordini");
%>
<div class="ContProfilo">
    <div class="ProfiloInfoDiv">
        <div><h1>Informazioni per l'utente:</h1></div>
        <div><h3>Nome utente: <%=utente.getUsername()%></h3></div>
        <div><h3>Nome: <%=utente.getNome()%></h3></div>
        <div><h3>Cognome: <%=utente.getCognome()%></h3></div>
        <div><h3>E-mail: <%=utente.getEmail()%></h3></div>
        <div><h3>Telefono: <%=utente.getTelefono()%></h3></div>
        <div><h3>Città: <%=utente.getCitta()%></h3></div>
        <div><h3>CAP: <%=utente.getCAP()%></h3></div>
        <div><h3>Via: <%=utente.getVia()%></h3></div>
    </div>
    <div class="Ordini">
        <div><h2>Lista ordini:</h2></div>
        <%
            DecimalFormat df = new DecimalFormat("#.00");
            if(ordini.isEmpty()){%>
        <div style="color: red"><h4>Nessun ordine effettuato</h4></div>
        <%}
        else{
            int contatore = 0;
            for(Ordine o: ordini){
                contatore++;
        %>
        <div><h3 style="color: red"><a href="FatturaServlet?numeroOrdine=<%=o.getId()%>">ORDINE n. <%=contatore%>:</a></h3></div>
        <div><h3>Data ordine: <%=o.getDataOrdine()%></h3></div>
        <div><h3>Numero di zaini comprati: <%=o.getNumZaino()%></h3></div>
        <div><h3>Prezzo totale: <%=df.format(o.getTotale())%> €</h3></div>
        <%}
        }
        %>
    </div>
    <div><a href="LogOutServlet"><button>Log-out</button></a></div>
</div>

<div class="finalInfo">
    <div class="Icone">
        <div>
            <img style="width: 30px; height: 30px" src="CSS/FaceBookImg.svg" alt="Checkk">
            <p>BookShopPage</p>
        </div>
        <div>
            <img style="width: 30px; height: 30px" src="CSS/InstaImg.svg" alt="Checkk">
            <p>@BookShop</p>
        </div>
        <div>
            <img style="width: 30px; height: 30px" src="CSS/Twitter.svg" alt="Checkk">
            <p>BookShopTW</p>
        </div>
    </div>

</div>
</body>
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
