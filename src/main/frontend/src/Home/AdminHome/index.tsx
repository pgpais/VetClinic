import React, { ChangeEvent, useEffect, useState, FormEvent } from "react";
import { requestAdminRegister } from "./actions";
import { GlobalState } from "../../App";
import { connect } from "react-redux";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";

const Register = (props: {
  performRegister: (
    username: string,
    password: string,
    name: string,
    photo: string,
    email: string,
    phone: string,
    address: string,
    employeeId: string
  ) => void;
}) => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [name, setName] = useState("");
  const [photo, setPhoto] = useState("");
  const [email, setEmail] = useState("");
  const [phone, setPhone] = useState("");
  const [address, setAddress] = useState("");
  const [employeeId, setEmployeeId] = useState("110841e3-e6fb-4191-8fd8-5674a5107c3a");
  const [isRegister, setRegister] = useState(false);

  let registerSubmitHandler = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    props.performRegister(
      username,
      password,
      name,
      photo,
      email,
      phone,
      address,
      employeeId
    );
    setUsername("");
    setPassword("");
    setName("");
    setPhoto("");
    setEmail("");
    setPhone("");
    setAddress("");
    setEmployeeId("110841e3-e6fb-4191-8fd8-5674a5107c3a");
  };

  let usernameChangeHandler = (e: ChangeEvent<HTMLInputElement>) => {
    setUsername(e.target.value);
  };

  //TODO: not being used? vets have name, no?
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

  let employeeIdChangeHandler = (e: ChangeEvent<HTMLInputElement>) => {
    setEmployeeId(e.target.value);
  };

  let registerForm = (
    <>
      <Form onSubmit={registerSubmitHandler}>
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
          <Form.Control
            type="text"
            value={name}
            onChange={nameChangeHandler}
          />
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
          <Form.Control
            type="text"
            value={photo}
            onChange={photoChangeHandler}
          />
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
        {/* <div>
          <label>
            EmployeeId:{" "}
            <input
              type="text"
              value={employeeId}
              onChange={employeeIdChangeHandler}
            />
          </label>
        </div> */}
        <button onClick={() => setRegister(false)}>Register</button>
      </Form>
    </>
  );
  return <> {registerForm} </>;
  /*TODO: fazer o register form desaparecer
    faz um botao que altere o estado (Vê o signIn)
    e depois fazes aquela cena do {isRegister && registerForm}
    e ele só mostra o register quando isso ficar a true.
    eventualmente vamos ter deletes e cenas assim, por isso mais vale ir escondendo
   */
};

const mapDispatchToProps = (dispatch: any) => ({
  performRegister: (
    username: string,
    password: string,
    name: string,
    photo: string,
    email: string,
    phone: string,
    address: string,
    employeeId: string
  ) => {
    dispatch(
      requestAdminRegister(
        username,
        password,
        name,
        photo,
        email,
        phone,
        address,
        employeeId
      )
    );
  }
});
const mapStateToProps = (state: GlobalState) => ({
  isSignedIn: state.signIn.isSignedIn
});

export const RegistrationAdmin = connect(
  mapStateToProps,
  mapDispatchToProps
)(Register);

const ShowRegistration = () => {
  let [isShowing, setIsShowing] = useState(false);

  return (
    <>
      {isShowing && <RegistrationAdmin />}
      <button onClick={() => setIsShowing(!isShowing)}>
        {isShowing ? "Cancel Registration" : "Register"}
      </button>
    </>
  );
};

const ProtoAdminHome = () => {
  return (
    <>
      <ShowRegistration />
    </>
  );
};

export default ProtoAdminHome;
