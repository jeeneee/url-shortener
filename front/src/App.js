import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import Home from "./page/Home";
import ShortUrl from "./page/ShortUrl";
import "./App.css";

function App() {
  return (
    <div className="App">
      <Router>
        <Switch>
          <Route exact path="/" component={Home}></Route>
          <Route path="/:shortUrl" component={ShortUrl}></Route>
        </Switch>
      </Router>
    </div>
  );
}

export default App;
