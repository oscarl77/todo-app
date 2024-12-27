import React from 'react';
import '../styles/Login.css'

import { Link } from 'react-router-dom';

function Login() {
    return (
        <div className={"form-container"}>
            <div className={"header"}>Login</div>
            <form>
                <div>
                    <input
                        className={"inputs"}
                        type={"email"} id={"email"}
                        placeholder={"Email"}
                    />
                </div>
                <div>
                    <input
                        className={"inputs"}
                        type={"password"}
                        id={"password"}
                        placeholder={"Password"}
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