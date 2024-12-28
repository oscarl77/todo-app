import React from 'react'
import { useState } from 'react'
import { useNavigate} from "react-router-dom";
import validator from "validator/es";
import '../styles/SignUp.css'

/**
 * This component represents the sign-up page
 * @returns {Element} the rendered sign-up form element
 * @constructor
 */
function SignUp() {

    const [username, setUsername] = useState('');
    const [usernameError, setUsernameError] = useState('');

    const [email, setEmail] = useState('');
    const [emailError, setEmailError] = useState('');

    const [password, setPassword] = useState('');
    const [passwordError, setPasswordError] = useState('');

    const [confirmPassword, setConfirmPassword] = useState('');
    const [confirmPasswordError, setConfirmPasswordError] = useState('');

    const navigate = useNavigate();

    /**
     * Checks if username entry is valid.
     * @param value inputted username.
     * @returns {boolean} true if valid, false otherwise.
     */
    const validateUsername = (value) => {
        setUsername(value)
        if(/\s/.test(value) || validator.isEmpty(value)) {
            setUsernameError('Username already used or invalid.')
            return false;
        } else {
            setUsernameError('')
            return true;
        }
    }

    /**
     * Checks if the email is a valid address.
     * @param value inputted email.
     * @return {boolean} true if valid, false otherwise.
     */
    const validateEmail = (value) => {
        setEmail(value);
        if (validator.isEmail(value)) {
            setEmailError('')
            return true;
        } else {
            setEmailError('Invalid email address')
            return false;
        }
    }

    /**
     * Checks if the password entry is valid.
     * @param value inputted password.
     * @return {boolean} true if valid, false otherwise.
     */
    const validatePassword = (value) => {
        setPassword(value);
        if (value.length > 7) {
            setPasswordError('')
            return true;
        } else {
            setPasswordError('Password must be at least 8 characters')
            return false;
        }
    }

    /**
     * Checks if the passwords match.
     * @param value the inputted password.
     * @return {boolean} true if passwords match, false otherwise.
     */
    const validateConfirmPassword = (value) => {
        setConfirmPassword(value)
        if (value === password) {
            setConfirmPasswordError('')
            return true;
        } else {
            setConfirmPasswordError('Passwords do not match')
            return false;
        }
    }

    /**
     * Check if all entries are initially valid
     * @returns {boolean} true if all entries are valid, false otherwise.
     */
    const isValidEntries = () => {
        return validateUsername(username) && validateEmail(email)
            && validatePassword(password) && validateConfirmPassword(password)
    }

    /**
     * Clear user details from memory.
     */
    const clearEntries = () => {
        setUsername('')
        setEmail('')
        setPassword('')
        setConfirmPassword('')
    }

    const getCsrfToken = () => {
        return document.cookie.split(';')
            .find(cookie => cookie.trim().startsWith('XSRF-TOKEN='))
            ?.split('=')[1];
    };

    /**
     * Navigates to the email verification page if inputted details are valid.
     */
    const handleSubmit = async (e) => {
        e.preventDefault()

        const csrfToken = getCsrfToken()

        if (!isValidEntries()) {
            return;
        }

        const response = await fetch("http://localhost:8080/api/users", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "X-XSRF-TOKEN": csrfToken
            },
            body: JSON.stringify({
                    username: username,
                    email: email,
                    password: password
                }
            ),
            credentials: "include"
        });

        if (response.ok) {
            navigate('/verification', { state: { email} })
            return;
        }
        const errorData = await response.text()
        if (errorData === "Username taken") {
            setUsernameError("Username taken")
            return;
        }
        if (errorData === "Email taken") {
            setEmailError("Email taken")
            return;
        }
        clearEntries()
    }

    return (
        <div className={"form-container"}>
            <div className={"header"}>Create Account</div>
            <form>
                <div className={"input-container"}>
                    <input
                        className={"inputs"}
                        type={"text"}
                        id={"username"}
                        placeholder={"Username"}
                        value={username}
                        onChange={(e) => validateUsername(e.target.value)}
                    />
                    {usernameError && <span className={"error-message"}>{usernameError}</span>}
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
                    {emailError && <span className={"error-message"}>{emailError}</span>}
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
                    {passwordError && <span className={"error-message"}>{passwordError}</span>}
                </div>
                <div className={"input-container"}>
                    <input
                        className={"inputs"}
                        type={"password"}
                        id={"confirmPassword"}
                        placeholder={"Confirm Password"}
                        value={confirmPassword}
                        onChange={(e) => validateConfirmPassword(e.target.value)}
                    />
                    {confirmPasswordError && <span className={"error-message"}>{confirmPasswordError}</span>}
                </div>
            </form>
            <div className={"submit-container"}>
                <button className={"continue"} onClick={handleSubmit}>Continue</button>
            </div>
        </div>
    )
}

export default SignUp