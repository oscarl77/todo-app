import React from 'react'
import { useState } from 'react'
import { Link } from 'react-router-dom'
import validator from "validator/es";
import '../styles/SignUp.css'

/**
 * This component represents the sign-up page
 * @returns {Element} the rendered sign-up form element
 * @constructor
 */
function SignUp() {

    const [email, setEmail] = useState('');
    const [emailError, setEmailError] = useState('');

    const [password, setPassword] = useState('')
    const [passwordError, setPasswordError] = useState('');

    const [setConfirmPassword] = useState('')
    const [confirmPasswordError, setConfirmPasswordError] = useState('');

    /**
     * Checks if the email is a valid address.
     * @param value inputted email.
     */
    const validateEmail = (value) => {
        setEmail(value);
        if (validator.isEmail(value)) {
            setEmailError('')
        } else {
            setEmailError('Invalid email address')
        }
    }

    /**
     * Checks if the password is greater than 8 characters.
     * @param value inputted password.
     */
    const validatePassword = (value) => {
        setPassword(value);
        if (value.length > 8) {
            setPasswordError('')
        } else {
            setPasswordError('Password must be at least 8 characters')
        }
    }

    /**
     * Checks if the passwords match.
     * @param value the inputted password.
     */
    const validateConfirmPassword = (value) => {
        setConfirmPassword(value)
        if (value === password) {
            setConfirmPasswordError('')
        } else {
            setConfirmPasswordError('Passwords do not match')
        }
    }

    return (
        <div className={"form-container"}>
            <div className={"header"}>Create Account</div>
            <form>
                <div>
                    <input
                        className={"inputs"}
                        type={"text"}
                        id={"username"}
                        placeholder={"Username"}
                    />
                </div>
                <div className={"input-container"}>
                    <input
                        className={"inputs"}
                        type={"email"}
                        id={"email"}
                        placeholder={"Email"}
                        value={email}
                        onChange={(e) => validateEmail(e.target.value)}
                    />
                    {emailError && <span className={"email-error"}>{emailError}</span>}
                </div>
                <div className={"input-container"}>
                    <input
                        className={"inputs"}
                        type={"password"}
                        id={"password"}
                        placeholder={"Password"}
                        value={password}
                        onChange={(e) => validatePassword(e.target.value)}
                    />
                    {passwordError && <span className={"password-error"}>{passwordError}</span>}
                </div>
                <div className={"input-container"}>
                    <input
                        className={"inputs"}
                        type={"password"}
                        id={"confirmPassword"}
                        placeholder={"Confirm Password"}
                        onChange={(e) => validateConfirmPassword(e.target.value)}
                    />
                    {confirmPasswordError && <span className={"confirm-password-error"}>{confirmPasswordError}</span>}
                </div>
            </form>
            <div className={"submit-container"}>
                <Link to={""}>
                    <button className={"continue"}>Continue </button>
                </Link>
            </div>
        </div>
    )
}

export default SignUp