import React from 'react'
import { Link } from 'react-router-dom'
import { useLocation } from 'react-router-dom'

import '../styles/EmailVerification.css'

function EmailVerification() {

    const location = useLocation();
    const email = location.state?.email;

    return (
        <div className={"main-container"}>
            <div className={"text-header"}>Verify your email address</div>
            <div className={"text-container"}>
                <p>Please verify your email address by clicking on the link sent to:</p>
                <p className={"email"}>{email}</p>
                <p>Once completed, you can login to your account below.</p>
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