import React, { ChangeEvent, useEffect, useState, FormEvent } from "react";
import Form from "react-bootstrap/Form";
import { VetListSelect } from "..";
import {
  requestUpdateAppointment,
  fetchApts,
  fetchAptsVet,
  fetchPendingAptsVet
} from "../../Appointment/actions";
import { connect } from "react-redux";
import { Appointment } from "../../Appointment";
import { GlobalState } from "../../App";

const ProtoAppointmentView = (props: { apt: Appointment }) => {
  let date = new Date(props.apt.date).toUTCString();
  return (
    <>
      Date: {date} <br />
      Client: {props.apt.client} <br />
      Pet: {props.apt.petId} <br />
      Veterinary: {props.apt.vetId} <br />
      Status: {props.apt.status} <br />
      Reason: {props.apt.reason}
    </>
  );
};

const AptList = (props: { apts: Appointment[] }) => (
  <ul>
    {props.apts.map((apt: Appointment) => (
      <li key={apt.id}>
        <ProtoAppointmentView apt={apt} />
      </li>
    ))}
  </ul>
);

export const ProtoAppointmentList = (props: {
  apts: Appointment[];
  loadApts: () => void;
}) => {
  useEffect(() => props.loadApts(), []);
  return <AptList apts={props.apts} />;
};

const mapStateToProps = (state: GlobalState) => ({ apts: state.apts.apts });
const mapDispatchToProps = (dispatch: any) => ({
  loadApts: () => {
    dispatch(fetchAptsVet());
  }
});

export const AppointmentList = connect(
  mapStateToProps,
  mapDispatchToProps
)(ProtoAppointmentList);

const AptSelect = (props: { apts: Appointment[] }) => (
  <>
    {props.apts.map((apt: Appointment) => (
      <option value={apt.id}>{apt.date}</option>
    ))}
  </>
);

export const ProtoAppointmentSelect = (props: {
  apts: Appointment[];
  loadPendingApts: () => void;
}) => {
  useEffect(() => props.loadPendingApts(), []);
  return <AptSelect apts={props.apts} />;
};

const mapStateToPropsSelect = (state: GlobalState) => ({
  apts: state.pendingApts.pending_apts
});
const mapDispatchToPropsSelect = (dispatch: any) => ({
  loadPendingApts: () => {
    dispatch(fetchPendingAptsVet());
  }
});

export const AppointmentSelect = connect(
  mapStateToPropsSelect,
  mapDispatchToPropsSelect
)(ProtoAppointmentSelect);

const ShowAppointments = () => {
  let [isShowing, setIsShowing] = useState(true);
  let [isCreatingAppointment, setCreatingAppointment] = useState(false);

  return (
    <>
      <button onClick={() => setIsShowing(!isShowing)}>
        {isShowing ? "Hide Appointments" : "Show Appointments"}
      </button>
      {isShowing && <AppointmentList />}
    </>
  );
};

const ProtoUpdate = (props: {
  performUpdate: (id: number, reason: string, mode: string) => void;
}) => {
  const [id, setId] = useState("");
  const [reason, setReason] = useState("");
  const [mode, setMode] = useState("");

  const [isUpdating, setUpdating] = useState(false);

  let registerSubmitHandler = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    props.performUpdate(parseInt(id), reason, mode);
    setId("");
    setReason("");
    setMode("");
  };

  let idChangeHandler = (e: ChangeEvent<HTMLInputElement>) => {
    setId(e.target.value);
  };

  let reasonChangeHandler = (e: ChangeEvent<HTMLInputElement>) => {
    setReason(e.target.value);
  };

  let modeChangeHandler = (e: ChangeEvent<HTMLInputElement>) => {
    setMode(e.target.value);
  };

  let updateForm = (
    <>
      <Form onSubmit={registerSubmitHandler}>
        <Form.Group controlId="exampleForm.ControlSelect1">
          <Form.Label>Appointment</Form.Label>
          <Form.Control
            as="select"
            value={id}
            initialvalue={""}
            placeholder={""}
            onChange={idChangeHandler}
          >
            <option></option>
            <AppointmentSelect />
          </Form.Control>
        </Form.Group>

        <Form.Group controlId="exampleForm.ControlSelect1">
          <Form.Label>Mode</Form.Label>
          <Form.Control as="select" value={mode} onChange={modeChangeHandler}>
            <option></option>
            <option>accepted</option>
            <option>rejected</option>
            <option>completed</option>
          </Form.Control>
        </Form.Group>

        <Form.Group controlId="exampleForm.ControlSelect1">
          <Form.Label>Reason</Form.Label>
          <Form.Control
            type="text"
            value={reason}
            onChange={reasonChangeHandler}
          ></Form.Control>
        </Form.Group>

        <button
          onClick={() => {
            setUpdating(false);
          }}
        >
          Update Appointment{" "}
        </button>
      </Form>
    </>
  );

  return updateForm;
};

const ShowUpdate = (props: {
  performUpdateAppointment: (id: number, reason: string, mode: string) => void;
}) => {
  let [isShowing, setIsShowing] = useState(false);

  return (
    <>
      <button onClick={() => setIsShowing(!isShowing)}>
        {isShowing ? "Cancel" : "Update Appointment"}{" "}
      </button>
      <>
        {isShowing && (
          <ProtoUpdate performUpdate={props.performUpdateAppointment} />
        )}
      </>
    </>
  );
};

const mapDispatchToPropsUpdate = (dispatch: any) => ({
  performUpdateAppointment: (id: number, reason: string, mode: string) => {
    dispatch(requestUpdateAppointment(id, reason, mode));
  }
});

export const UpdateAppointment = connect(
  null,
  mapDispatchToPropsUpdate
)(ShowUpdate);

const ProtoVetHome = () => {
  return (
    <>
      <UpdateAppointment />
      <ShowAppointments />
    </>
  );
};

export default ProtoVetHome;
