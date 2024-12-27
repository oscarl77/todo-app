import Login from "./components/Login.jsx"
import SignUp from './components/SignUp'
import EmailVerification from './components/EmailVerification.jsx'

import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

function App() {
    return (
        <Router>
            <div className="App">
                <Routes>
                    <Route path={"/"} element={<Login />} />
                    <Route path={"/signup"} element={<SignUp />} />
                    <Route path={"/verification"} element={<EmailVerification />} />
                </Routes>
            </div>
        </Router>
    )
}

export default App
