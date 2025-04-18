import React from 'react'
import { Link } from 'react-router-dom'
import { useLocation } from 'react-router-dom'

import '../styles/EmailVerification.css'

function EmailVerification() {

    const location = useLocation();
    //const email = location.state?.email;

    return (
        <div className={"main-container"}>
            <div className={"text-header"}>Account successfully created.</div>
            <div className={"text-container"}>
                <p>You can now return to the login page.</p>
                <div className={"button-container"}>
                    <Link to={"/"}>
                        <button className={"return-to-login"}>Login</button>
                    </Link>
                </div>
            </div>
        </div>
    )
}

export default EmailVerification