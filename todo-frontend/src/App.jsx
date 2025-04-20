import Login from "./pages/Login.jsx"
import SignUp from './pages/SignUp.jsx'
import EmailVerification from './pages/EmailVerification.jsx'
import Dashboard from './pages/Dashboard.jsx'

import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

function App() {
    return (
        <Router>
            <div className="App">
                <Routes>
                    <Route path={"/"} element={<Login />} />
                    <Route path={"/signup"} element={<SignUp />} />
                    <Route path={"/verification"} element={<EmailVerification />} />
                    <Route path={"/dashboard"} element={<Dashboard />} />
                </Routes>
            </div>
        </Router>
    )
}

export default App
