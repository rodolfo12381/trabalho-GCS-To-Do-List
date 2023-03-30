import Home from "./pages/Home";
import "./App.css"
import NavbarComponent from "./components/NavbarComponent";
import Footer from "./components/Footer";

function App() {
  return (
    <div className="App">
      <NavbarComponent/>
      <Home/>
      <Footer/>
    </div>
  );
}

export default App;
