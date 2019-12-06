import React, { ChangeEvent, useEffect, useState, FormEvent } from "react";
import {
  FilteredPetList,
  PetRegistration,
  FilteredPetSelect
} from "../../Pets";
import {
    Appointment,
    AppointmentList, AppointmentRegistration,
    ProtoAppointmentList, ProtoAppointmentRegistration
} from "../../Appointment";
import { connect } from "react-redux";
import { requestAppointmentRegister } from "../../Appointment/actions";
import Form from "react-bootstrap/Form";
import FormGroup from "react-bootstrap/FormGroup";
import { requestPetDelete } from "../../Pets/actions";





const ShowAppointments = () => {
  let [isShowing, setIsShowing] = useState(true);
  let [isCreatingAppointment, setCreatingAppointment] = useState(false);

  return (
    <>
      <button onClick={() => setIsShowing(!isShowing)}>
        {isShowing ? "Hide Appointments" : "Show Appointments"}
      </button>
      {isShowing && <AppointmentList />}
      <button onClick={() => setCreatingAppointment(!isCreatingAppointment)}>
        {isCreatingAppointment ? "Cancel" : "Create Appointment"}{" "}
      </button>

      {isCreatingAppointment && <AppointmentRegistration />}
    </>
  );
};

const ProtoDelete = (props: { performDelete: (id: number) => void }) => {
  const [id, setId] = useState("");

  const [isDeleting, setDeleting] = useState(false);

  let registerSubmitHandler = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    props.performDelete(parseInt(id));
    setId("");
  };

  let idChangeHandler = (e: ChangeEvent<HTMLInputElement>) => {
    setId(e.target.value);
  };

  let deletionForm = (
    <>
      <Form onSubmit={registerSubmitHandler}>
        <Form.Group controlId="exampleForm.ControlSelect1">
          <Form.Label>Pet</Form.Label>
          <Form.Control
            as="select"
            value={id}
            initialvalue={""}
            placeholder={""}
            onChange={idChangeHandler}
          >
            <option></option>
            <FilteredPetSelect />
          </Form.Control>
        </Form.Group>

        <button
          onClick={() => {
            setDeleting(false);
          }}
        >
          Delete Pet
        </button>
      </Form>
    </>
  );

  return deletionForm;
};

const ShowDelete = (props: { performDeletePet: (id: number) => void }) => {
  let [isShowing, setIsShowing] = useState(false);

  return (
    <>
      <button onClick={() => setIsShowing(!isShowing)}>
        {isShowing ? "Cancel" : "Delete Pet"}{" "}
      </button>
      <>{isShowing && <ProtoDelete performDelete={props.performDeletePet} />}</>
    </>
  );
};

const mapDispatchToPropsDelete = (dispatch: any) => ({
  performDeletePet: (id: number) => {
    dispatch(requestPetDelete(id));
  }
});

export const PetDeletion = connect(null, mapDispatchToPropsDelete)(ShowDelete);

const ProtoClientHome = () => {
  return (
    <>
      <FilteredPetList />
      <PetRegistration />
      <ShowAppointments />
      <PetDeletion />
    </>
  );
};

export default ProtoClientHome;
