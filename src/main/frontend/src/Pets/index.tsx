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

import React, { ChangeEvent, useEffect, useState, FormEvent } from "react";
import { connect } from "react-redux";
import { fetchPets, requestPetRegister } from "./actions";
import { GlobalState } from "../App";
import "./Pets.css";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";

export interface Pet {
  id: number;
  name: string;
  photo: string;
}
export interface PetState {
  pets: Pet[];
  isFetching: boolean;
}

//TODO: create Pet Component to be able to operate it (create appointments and such)

const Pet = (props: { pet: Pet }) => {
  let [showApt, setShowApt] = useState(false);

  //TODO: create separate folder for a single Pet

  return (
    <>
      <div className={"entityView"} onClick={() => setShowApt(!showApt)}>
        <img src={props.pet.photo} alt={"Pet " + props.pet.name + " photo"} />{" "}
        <br />
        Name: {props.pet.name} | ID: {props.pet.id}
      </div>
    </>
  );
};

const PetList = (props: { pets: Pet[] }) => (
  <ul>
    {props.pets.map((pet: Pet) => (
      <li key={pet.id}>
        <Pet pet={pet} />
      </li>
    ))}
  </ul>
);

const ProtoFilteredPetList = (props: {
  pets: Pet[];
  username: string;
  loadPets: (username: string, filter: string) => void;
}) => {
  const [filter, setFilter] = useState("");
  let handle = (e: ChangeEvent<HTMLInputElement>) => setFilter(e.target.value);
  // eslint-disable-next-line
  useEffect(() => props.loadPets(props.username, filter), [filter]);

  return (
    <>
      <PetList pets={props.pets} />
      <input onChange={handle} value={filter} />
    </>
  );
};

const mapStateToProps = (state: GlobalState) => ({
  username: state.signIn.username,
  pets: state.pets.pets
});
const mapDispatchToProps = (dispatch: any) => ({
  loadPets: (username: string, filter: string) => {
    dispatch(fetchPets(username, filter));
  }
});
export const FilteredPetList = connect(
  mapStateToProps,
  mapDispatchToProps
)(ProtoFilteredPetList);

const PetListSelect = (props: { pets: Pet[] }) => (
  <>
    {props.pets.map((pet: Pet) => (
      <option value={pet.id}>{pet.name}</option>
    ))}
  </>
);

const ProtoFilteredPetSelect = (props: {
  pets: Pet[];
  username: string;
  loadPets: (username: string, filter: string) => void;
}) => {
  const [filter, setFilter] = useState("");
  let handle = (e: ChangeEvent<HTMLInputElement>) => setFilter(e.target.value);
  // eslint-disable-next-line
  useEffect(() => props.loadPets(props.username, filter), [filter]);

  return (
    <>
      <PetListSelect pets={props.pets} />
    </>
  );
};

const mapStateToProps2 = (state: GlobalState) => ({
  username: state.signIn.username,
  pets: state.pets.pets
});
const mapDispatchToProps2 = (dispatch: any) => ({
  loadPets: (username: string, filter: string) => {
    dispatch(fetchPets(username, filter));
  }
});
export const FilteredPetSelect = connect(
  mapStateToProps2,
  mapDispatchToProps2
)(ProtoFilteredPetSelect);

const ProtoPetRegistration = (props: {
  performPetRegister: (
    name: string,
    species: string,
    photo: string,
    owner: string,
    appointments: any,
    chip: string,
    physDesc: string,
    healthDesc: string,
    removed: boolean
  ) => void;
}) => {
  const [name, setName] = useState("");
  const [species, setSpecies] = useState("");
  const [photo, setPhoto] = useState("");
  let user = localStorage.getItem("username");
  const [owner, setOwner] = useState(user ? user : "");
  const [appointments, setAppointments] = useState("");
  const [chip, setChip] = useState("110841e3-e6fb-4191-8fd8-5674a5107c3a");
  const [physDesc, setPhysDesc] = useState("");
  const [healthDesc, setHealthDesc] = useState("");
  const [removed, setRemoved] = useState(false);

  const [isRegister, setRegister] = useState(false);

  let registerSubmitHandler = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    props.performPetRegister(
      name,
      species,
      photo,
      owner,
      appointments,
      chip,
      physDesc,
      healthDesc,
      removed
    );
    setSpecies("");
    setAppointments("");
    setName("");
    setPhoto("");
    setChip("110841e3-e6fb-4191-8fd8-5674a5107c3a");
    setHealthDesc("");
    setPhysDesc("");
    setRemoved(false);
    let user = localStorage.getItem("username");
    setOwner(user ? user : "");
  };

  let nameChangeHandler = (e: ChangeEvent<HTMLInputElement>) => {
    setName(e.target.value);
  };

  let photoChangeHandler = (e: ChangeEvent<HTMLInputElement>) => {
    setPhoto(e.target.value);
  };
  let speciesChangeHandler = (e: ChangeEvent<HTMLInputElement>) => {
    setSpecies(e.target.value);
  };

  let chipChangeHandler = (e: ChangeEvent<HTMLInputElement>) => {
    setChip(e.target.value);
  };

  let healthChangeHandler = (e: ChangeEvent<HTMLInputElement>) => {
    setHealthDesc(e.target.value);
  };

  let physChangeHandler = (e: ChangeEvent<HTMLInputElement>) => {
    setPhysDesc(e.target.value);
  };

  let ownerChangeHandler = (e: ChangeEvent<HTMLInputElement>) => {
    setOwner(e.target.value);
  };

  let registerForm = (
    <>
      <Form onSubmit={registerSubmitHandler}>
        <Form.Group controlId="formBasicUsername">
          <Form.Label>Name</Form.Label>
          <Form.Control type="text" value={name} onChange={nameChangeHandler} />
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
          <Form.Label>species</Form.Label>
          <Form.Control
            type="text"
            value={species}
            onChange={speciesChangeHandler}
          />
          <Form.Text className="text-muted"></Form.Text>
        </Form.Group>
        {/* <div>
      <label>
        Name: <input type="text" value={name} onChange={nameChangeHandler} />
      </label>
    </div> */}
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
          <Form.Label>Owner</Form.Label>
          <Form.Control
            type="text"
            value={owner}
            readOnly
            placeholder={owner}
          />
          <Form.Text className="text-muted"></Form.Text>
        </Form.Group>
        {/* <div>
      <label>
        phone:{" "}
        <input type="number" value={phone} onChange={phoneChangeHandler} />
      </label>
    </div> */}

        {/* <div>
      <label>
        Address:{" "}
        <input type="text" value={address} onChange={addressChangeHandler} />
      </label>
    </div> */}

        <Form.Group controlId="formBasicPhone">
          <Form.Label>Physical Description</Form.Label>
          <Form.Control
            type="text"
            value={physDesc}
            onChange={physChangeHandler}
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

        <Form.Group controlId="formBasicEmployeeID">
          <Form.Label>Health Description</Form.Label>
          <Form.Control
            type="text"
            value={healthDesc}
            onChange={healthChangeHandler}
          />
        </Form.Group>
        <button onClick={() => setRegister(false)}>Register Pet</button>
      </Form>
    </>
  );

  return registerForm;
};

const ShowRegistration = (props: {
  performPetRegister: (
    name: string,
    species: string,
    photo: string,
    owner: string,
    appointments: any,
    chip: string,
    physDesc: string,
    healthDesc: string,
    removed: boolean
  ) => void;
}) => {
  let [isShowing, setIsShowing] = useState(false);

  return (
    <>
      {isShowing && (
        <ProtoPetRegistration performPetRegister={props.performPetRegister} />
      )}
      <button onClick={() => setIsShowing(!isShowing)}>
        {isShowing ? "Cancel Registration" : "Register Pet"}
      </button>
    </>
  );
};

const mapDispatchToProps1 = (dispatch: any) => ({
  performPetRegister: (
    name: string,
    species: string,
    photo: string,
    owner: any,
    appointments: any,
    chip: string,
    physDesc: string,
    healthDesc: string,
    removed: boolean
  ) => {
    dispatch(
      requestPetRegister(
        name,
        species,
        photo,
        owner,
        appointments,
        chip,
        physDesc,
        healthDesc,
        removed
      )
    );
  }
});

export const PetRegistration = connect(
  null,
  mapDispatchToProps1
)(ShowRegistration);
