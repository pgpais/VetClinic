import React, { ChangeEvent, useEffect, useState, FormEvent } from "react";
import { requestAdminRegister } from "./actions";
import { GlobalState } from "../../App";
import { connect } from "react-redux";
import Form from "react-bootstrap/Form";

const ProtoAdminHome = (props: {
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
  const [employeeId, setEmployeeId] = useState("");

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
    setEmployeeId("");
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

  let employeeIdChangeHandler = (e: ChangeEvent<HTMLInputElement>) => {
    setEmployeeId(e.target.value);
  };

  let registerForm = (
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
        <Form.Label>Name</Form.Label>
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
        <Form.Control type="text" value={photo} onChange={photoChangeHandler} />
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
      <div>
        <label>
          EmployeeId:{" "}
          <input
            type="text"
            value={employeeId}
            onChange={employeeIdChangeHandler}
          />
        </label>
      </div>

      <Form.Group controlId="formBasicEmployeeID">
        <Form.Label>Employee Id</Form.Label>
        <Form.Control
          type="text"
          value={employeeId}
          onChange={employeeIdChangeHandler}
        />
        <Form.Text className="text-muted">
          Has to be something like 110841e3-e6fb-4191-8fd8-5674a5107c3
        </Form.Text>
      </Form.Group>
      <button>Register</button>
    </Form>
  );
  return <> {registerForm} </>;
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

const AdminHome = connect(mapStateToProps, mapDispatchToProps)(ProtoAdminHome);

export default AdminHome;
