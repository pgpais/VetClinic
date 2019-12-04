/**
 Copyright 2019 João Costa Seco, Eduardo Geraldo

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

import React, { ChangeEvent, FormEvent, useState } from "react";
import { connect } from "react-redux";
import { GlobalState } from "../App";
import {
  requestRegister,
  requestSignIn,
  SignInAction,
  signOut
} from "./actions";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Navbar from "react-bootstrap/Navbar";
import Nav from "react-bootstrap/Nav";
import NavDropdown from "react-bootstrap/NavDropdown";
import FormControl from "react-bootstrap/FormControl";

export interface SignInState {
  isSignedIn: boolean;
  role: string;
  username: string;
}

const ProtoSignInForm = (props: {
  isSignedIn: boolean;
  performSignIn: (username: string, pass: string) => void;
  performSignOut: () => void;
  performRegister: (
    username: string,
    password: string,
    name: string,
    photo: string,
    email: string,
    phone: string,
    address: string
  ) => void;
}) => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [name, setName] = useState("");
  const [photo, setPhoto] = useState("");
  const [email, setEmail] = useState("");
  const [phone, setPhone] = useState("");
  const [address, setAddress] = useState("");
  const [isRegister, setRegister] = useState(false);

  let submitHandler = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    console.log("login submit");
    props.performSignIn(username, password);
    setUsername("");
    setPassword("");
  };

  let registerSubmitHandler = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    props.performRegister(
      username,
      password,
      name,
      photo,
      email,
      phone,
      address
    );
    setUsername("");
    setPassword("");
    setName("");
    setPhoto("");
    setEmail("");
    setPhone("");
    setAddress("");
    setRegister(false);
  };

  let handlerLogout = (e: FormEvent<HTMLButtonElement>) => {
    props.performSignOut();
  };

  let usernameChangeHandler = (e: ChangeEvent<HTMLInputElement>) => {
    setUsername(e.target.value);
  };
  let nameChangeHandler = (e: ChangeEvent<HTMLInputElement>) => {
    setName(e.target.value);
  };
  let photoChangeHandler = (e: ChangeEvent<HTMLInputElement>) => {
    setPhoto(e.target.value);
  };
  let emailChangeHandler = (e: ChangeEvent<HTMLInputElement>) => {
    setEmail(e.target.value);
  };
  let phoneChangeHandler = (e: ChangeEvent<HTMLInputElement>) => {
    setPhone(e.target.value);
  };
  let addressChangeHandler = (e: ChangeEvent<HTMLInputElement>) => {
    setAddress(e.target.value);
  };

  let passwordChangeHandler = (e: ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  };

  let registerForm = (<>
    <Form onSubmit={registerSubmitHandler}>
      {/* <div>
        <label>
          Username:{" "}
          <input
            type="text"
            value={username}
            onChange={usernameChangeHandler}
          />
        </label>
      </div> */}

      <Form.Group controlId="formBasicUsername">
        <Form.Label>Username</Form.Label>
        <Form.Control
          type="text"
          value={username}
          onChange={usernameChangeHandler}
        />
        <Form.Text className="text-muted"></Form.Text>
      </Form.Group>

      {/* <div>
        <label>
          Password:{" "}
          <input
            type="password"
            value={password}
            onChange={passwordChangeHandler}
          />
        </label>
      </div> */}

      <Form.Group controlId="formBasicPassword">
        <Form.Label>Password</Form.Label>
        <Form.Control
          type="password"
          value={password}
          onChange={passwordChangeHandler}
        />
        <Form.Text className="text-muted"></Form.Text>
      </Form.Group>
      {/* <div>
        <label>
          Name: <input type="text" value={name} onChange={nameChangeHandler} />
        </label>
      </div> */}

      <Form.Group controlId="formBasicName">
        <Form.Label>Name</Form.Label>
        <Form.Control type="text" value={name} onChange={nameChangeHandler} />
        <Form.Text className="text-muted">
          {" "}
          First and Last names please
        </Form.Text>
      </Form.Group>
      {/* <div>
        <label>
          Photo:{" "}
          <input type="text" value={photo} onChange={photoChangeHandler} />
        </label>
      </div> */}

      <Form.Group controlId="formBasicPhoto">
        <Form.Label>Photo URL</Form.Label>
        <Form.Control type="text" value={photo} onChange={photoChangeHandler} />
        <Form.Text className="text-muted"></Form.Text>
      </Form.Group>
      {/* <div>
        <label>
          Email:{" "}
          <input type="text" value={email} onChange={emailChangeHandler} />
        </label>
      </div> */}

      <Form.Group controlId="formBasicEmail">
        <Form.Label>Email address</Form.Label>
        <Form.Control
          type="text"
          value={email}
          onChange={emailChangeHandler}
          placeholder="name@example.com"
        />
        <Form.Text className="text-muted">
          We'll never share your email with anyone else.
        </Form.Text>
      </Form.Group>
      {/* <div>
        <label>
          phone:{" "}
          <input type="number" value={phone} onChange={phoneChangeHandler} />
        </label>
      </div> */}

      <Form.Group controlId="formBasicPhone">
        <Form.Label>Phone Number</Form.Label>
        <Form.Control
          type="number"
          value={phone}
          onChange={phoneChangeHandler}
        />
        <Form.Text className="text-muted"></Form.Text>
      </Form.Group>
      {/* <div>
        <label>
          Address:{" "}
          <input type="text" value={address} onChange={addressChangeHandler} />
        </label>
      </div> */}

      <Form.Group controlId="formBasicPhone">
        <Form.Label>Address</Form.Label>
        <Form.Control
          type="text"
          value={address}
          onChange={addressChangeHandler}
        />
        <Form.Text className="text-muted"></Form.Text>
      </Form.Group>
      <button type={"submit"}>Register</button>
    </Form>
    <button onClick={() => setRegister(false)}>Cancel</button>
    </>
  );

  let signInForm = (<>
    <Form onSubmit={submitHandler}>
      <Form.Group controlId="formBasicUsername">
        <Form.Label>Username</Form.Label>
        <Form.Control
            type="text"
            value={username}
            onChange={usernameChangeHandler}
        />
        <Form.Text className="text-muted"></Form.Text>
      </Form.Group>
      <Form.Group controlId="formBasicPassword">
        <Form.Label>Password</Form.Label>
        <Form.Control
            type="password"
            value={password}
            onChange={passwordChangeHandler}
        />
        <Form.Text className="text-muted"></Form.Text>
      </Form.Group>
      <Button type={"submit"}>Sign In</Button>
    </Form>
    <Button onClick={() => setRegister(true)}> Register </Button>
    </>
  );

  let signOutForm =
    <>
      Logged in as {localStorage.getItem("username")} <br/>
      <Button onClick={handlerLogout}>Sign out</Button>
    </>;

  return (
    <>
      {isRegister ?
          registerForm
        : props.isSignedIn ?
              signOutForm
            : signInForm}
    </>
  );
  // add a message space for alerts (you were signed out, expired session)
};
const mapStateToProps = (state: GlobalState) => ({
  isSignedIn: state.signIn.isSignedIn
});
const mapDispatchToProps = (dispatch: any) => ({
  performSignIn: (username: string, pass: string) => {
    dispatch(requestSignIn(username, pass));
  },
  performSignOut: () => dispatch(signOut()),
  performRegister: (
    username: string,
    password: string,
    name: string,
    photo: string,
    email: string,
    phone: string,
    address: string
  ) => {
    dispatch(
      requestRegister(username, password, name, photo, email, phone, address)
    );
  }
});
const SignInForm = connect(
  mapStateToProps,
  mapDispatchToProps
)(ProtoSignInForm);

export default SignInForm;
