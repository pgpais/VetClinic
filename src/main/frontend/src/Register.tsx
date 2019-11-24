import React, {ChangeEvent, FormEvent, useState} from "react";

async function performRegister(name: string, username: string, pass: string, photo: string, email: string, phone: string, address: string) {
    const myHeaders = new Headers();
    myHeaders.append('Content-Type', 'application/json');

    fetch("/home/register",
        {method:'POST',
            headers: myHeaders,
            body: JSON.stringify({name:name, username:username, pass:pass, photo:photo, email:email, phone:phone, address:address})})
        .then( response => {
            if( response.ok )
                return response.headers.get('Authorization');
            else {
                console.log(`Error: ${response.status}: ${response.statusText}`)
                return null;
                // and add a message to the Ui: wrong pass ?? other errors?
            }
        })
        .catch( err => { console.log(err) })
        .then( token => {
            if (token ) {
                localStorage.setItem('jwt', token);
                // not the safest of ways... but usable for now.
            }
        })
}

const RegisterForm = () => {
    const [ name, setName] = useState("");
    const [ username, setUsername ] = useState("");
    const [ pass, setpass ] = useState("");
    const [ photo, setPhoto] = useState("");
    const [ email, setEmail] = useState("");
    const [ phone, setPhone] = useState("");
    const [ address, setAddress] = useState("");

    let submitHandler = (e:FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        performRegister(name, username, pass, photo, email, phone, address);
        setName("");
        setUsername("");
        setpass("");
        setPhoto("");
        setEmail("");
        setPhone("");
        setAddress("");
    };

    let nameChangeHandler = (e:ChangeEvent<HTMLInputElement>) => { setName(e.target.value) };
    let usernameChangeHandler = (e:ChangeEvent<HTMLInputElement>) => { setUsername(e.target.value) };
    let passChangeHandler = (e:ChangeEvent<HTMLInputElement>) => { setpass(e.target.value) };
    let photoChangeHandler = (e:ChangeEvent<HTMLInputElement>) => { setPhoto(e.target.value)};
    let emailChangeHandler = (e:ChangeEvent<HTMLInputElement>) => { setEmail(e.target.value)};
    let phoneChangeHandler = (e:ChangeEvent<HTMLInputElement>) => { setPhone(e.target.value)};
    let addressChangeHandler = (e:ChangeEvent<HTMLInputElement>) => { setAddress(e.target.value)};

    let registerForm = <form onSubmit={submitHandler}>
        <div><label>Name: <input type="text" value={name} onChange={nameChangeHandler}/></label></div>
        <div><label>Username: <input type="text" value={username} onChange={usernameChangeHandler}/></label></div>
        <div><label>pass: <input type="pass" value={pass} onChange={passChangeHandler}/></label></div>
        <div><label>Photo: <input type="text" value={photo} onChange={photoChangeHandler}/></label></div>
        <div><label>Email: <input type="text" value={email} onChange={emailChangeHandler}/></label></div>
        <div><label>Phone: <input type="text" value={phone} onChange={phoneChangeHandler}/></label></div>
        <div><label>Address: <input type="text" value={address} onChange={addressChangeHandler}/></label></div>

        <button>Register</button>
    </form>;

    return registerForm;
};

export default RegisterForm