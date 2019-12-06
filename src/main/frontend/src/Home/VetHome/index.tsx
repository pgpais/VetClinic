import React, { ChangeEvent, useEffect, useState, FormEvent } from "react";
import Form from "react-bootstrap/Form";
import { VetListSelect } from "..";
import { requestUpdateAppointment } from "../../Appointment/actions";
import { connect } from "react-redux";

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
            <VetListSelect />
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
          Update UpdateAppointment{" "}
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
      {" "}
      <UpdateAppointment />{" "}
    </>
  );
};

export default ProtoVetHome;
