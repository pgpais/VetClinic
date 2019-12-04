import React, { ChangeEvent, useEffect, useState, FormEvent } from "react";
import { requestAdminRegister } from "./actions";
import { GlobalState } from "../../App";
import { connect } from "react-redux";

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
    <form onSubmit={registerSubmitHandler}>
      <div>
        <label>
          Username:{" "}
          <input
            type="text"
            value={username}
            onChange={usernameChangeHandler}
          />
        </label>
      </div>
      <div>
        <label>
          Password:{" "}
          <input
            type="password"
            value={password}
            onChange={passwordChangeHandler}
          />
        </label>
      </div>
      <div>
        <label>
          Name: <input type="text" value={name} onChange={nameChangeHandler} />
        </label>
      </div>
      <div>
        <label>
          Photo:{" "}
          <input type="text" value={photo} onChange={photoChangeHandler} />
        </label>
      </div>
      <div>
        <label>
          Email:{" "}
          <input type="text" value={email} onChange={emailChangeHandler} />
        </label>
      </div>
      <div>
        <label>
          phone:{" "}
          <input type="number" value={phone} onChange={phoneChangeHandler} />
        </label>
      </div>
      <div>
        <label>
          Address:{" "}
          <input type="text" value={address} onChange={addressChangeHandler} />
        </label>
      </div>
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
      <button>Register</button>
    </form>
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
