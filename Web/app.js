// Imports from config folder
const { app } = require('./config/config')
const cookieParser = require("cookie-parser");

const checkSessionCookie = require("./middlewares/checkSession");

// PORT variable
const PORT = 3000;

const {
  initializeApp,
  applicationDefault,
  cert,
} = require("firebase-admin/app");

const {
    getAuth
} = require("firebase-admin/auth")

const {
  getFirestore,
  Timestamp,
  FieldValue,
} = require("firebase-admin/firestore");

const admin = require("firebase-admin")

const serviceAccount = require("./AccountService.json");

initializeApp({
  credential: cert(serviceAccount),
});

const db = getFirestore();

const auth = getAuth();

app.use(cookieParser())

// Initializing server
app.listen(PORT, () => {
    console.log(`Servidor aberto na link: http://localhost:${PORT}/`)
})

// Main route
app.get('/', checkSessionCookie, (req, res) => {
   
    res.render('index')
   
})

app.get('/login', (req, res) => {

    res.render('login')
    
})

app.post("/login", async(req, res) => {

    const expiresIn = 60 * 60 * 24 * 5 * 1000

    try {
        const decodedToken = await auth.verifyIdToken(req.body.idToken)
        auth.createSessionCookie(req.body.idToken, { expiresIn })
        .then(
            (sessionCookie) => {
                const options = { maxAge: expiresIn, httpOnly: true }
                res.cookie("session", sessionCookie, options)
                console.log("Cookie Criado")
                res.end()
            }
        )
        
    } catch (e){
        console.log("Erro de Login")
    }

});

app.get('/register', (req, res) => {
    
    res.render('singup')

})

app.get('/read', checkSessionCookie, async (req, res) => {

    const dataSnapshot = await db
      .collection("Users")
      .doc(req.user.uid)
      .collection("Finances")
      .get();
    const items = [];
    dataSnapshot.forEach((doc) => {
      items.push({
        id: doc.id,
        name: doc.get("name"),
        price: doc.get("price"),
        date: doc.get("date"),
        profit: doc.get("profit")
      });
    });

    res.render('read', { items })

})

app.get('/create', checkSessionCookie, (req, res) => {
    
    res.render("create");

})

app.post("/create", checkSessionCookie, (req, res) => {
  const name = req.body.name;
  const price = parseFloat(req.body.price);
  const date = req.body.date
  const profit = req.body.profit === "true";

  var result = db
    .collection("Users")
    .doc(req.user.uid)
    .collection("Finances")
    .add({
      id: "",
      name: name,
      price: price,
      date: date,
      profit: profit,
    })
    .then(() => {
      res.redirect("/read");
    });
});

app.get('/details/:id', checkSessionCookie, async (req, res) => {

    const dataSnapshot = await db
      .collection("Users")
      .doc(req.user.uid)
      .collection("Finances")
      .doc(req.params.id)
      .get();

    const item = {
      id: dataSnapshot.id,
      name: dataSnapshot.get("name"),
      price: dataSnapshot.get("price"),
      date: dataSnapshot.get("date"),
      profit: dataSnapshot.get("profit"),
    };

    res.render('details', { item })

})

app.post("/edit", checkSessionCookie, async (req, res) => {

    const name = req.body.name;
    const price = parseFloat(req.body.price);
    const date = req.body.date;
    const profit = req.body.profit === "true";

  db.collection("Users")
    .doc(req.user.uid)
    .collection("Finances")
    .doc(req.body.id)
    .set({
      name: name,
      price: price,
      date: date,
      profit: profit,
    })
    .then(function () {
      console.log("Updated document");
      res.redirect("/read");
    });
});

app.post("/delete/:id", checkSessionCookie, (req, res) => {
  db.collection("Users")
    .doc(req.user.uid)
    .collection("Finances")
    .doc(req.params.id)
    .delete()
    .then(function () {
      console.log("Deleted document");
      res.redirect("/read");
    });
});

app.get('/signout', async (req, res) => {

    res.clearCookie("session")
    res.redirect("/login")

})