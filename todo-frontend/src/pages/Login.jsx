import React, {useState} from 'react';
import '../styles/Login.css'

import { Link } from 'react-router-dom';

function Login() {

    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [error, setError] = useState('')

    const getCsrfToken = () => {
        return document.cookie.split(';')
            .find(cookie => cookie.trim().startsWith('XSRF-TOKEN='))
            ?.split('=')[1];
    };

    const handleLogin = async () => {
        const csrfToken = getCsrfToken();

        const response = await fetch("http://localhost:8080/api/auth", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "X-XSRF-TOKEN": csrfToken
                },
                body: JSON.stringify({
                    email: email,
                    password: password
                }),
                credentials: "include"
            });

        if (response.ok) {
            console.log("Login successful")
            return;
        }

        const errorData = await response.text()
        console.log(errorData)
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
                <button className={"submit"} onClick={handleLogin}>Login</button>
                <Link to={"/signup"}>
                    <button className={"submit"}>Sign Up</button>
                </Link>
            </div>
        </div>
    )
}

export default Login;