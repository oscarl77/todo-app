import React, {useState} from 'react';
import '../styles/Login.css'

import { Link } from 'react-router-dom';
import axios from "axios";

function Login() {

    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [error, setError] = useState('')

    const handleLogin = async () => {
        const response = axios.post(email, password)

    }


    return (
        <div className={"form-container"}>
            <div className={"header"}>Login</div>
            <form>
                <div>
                    <input
                        className={"inputs"}
                        type={"email"}
                        id={"email"}
                        placeholder={"Email"}
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                </div>
                <div>
                    <input
                        className={"inputs"}
                        type={"password"}
                        id={"password"}
                        placeholder={"Password"}
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </div>
            </form>
            <button className={"forgot-password"}>Forgot Password?</button>
            <div className={"submit-container"}>
                <button className={"submit"}>Login</button>
                <Link to={"/signup"}>
                    <button className={"submit"}>Sign Up</button>
                </Link>
            </div>
        </div>
    )
}

export default Login;