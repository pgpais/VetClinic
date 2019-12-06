import React, {ChangeEvent, FormEvent, useEffect, useState} from "react";
import {connect} from "react-redux";
import {GlobalState} from "../App";
import {fetchApts, requestAppointmentRegister} from "./actions";
import Form from "react-bootstrap/Form";
import FormGroup from "react-bootstrap/FormGroup";
import {FilteredPetSelect} from "../Pets";

export interface Appointment {id:number, client:string, petId:string, vetId:string, aptDate:Date}
export interface AptState {apts:Appointment[], isFetchingApts:boolean}

export const ProtoAppointmentRegistration = (props: {
    performAppointmentRegister: (
        date: any,
        time: any,
        description: string,
        status: string,
        reason: string,
        pet: any,
        client: any,
        vet: any
    ) => void;
}) => {
    const [date, setDate] = useState("");
    const [time, setTime] = useState("");

    const [description, setDescription] = useState("");
    const [status, setStatus] = useState("PENDING");
    const [reason, setReason] = useState("");
    const [pet, setPet] = useState("");
    let user = localStorage.getItem("username");
    const [client, setClient] = useState(user ? user : "");
    const [vet, setVet] = useState("");

    const [isRegister, setRegister] = useState(false);

    let registerSubmitHandler = (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        props.performAppointmentRegister(
            date,
            time,
            description,
            status,
            reason,
            pet,
            client,
            vet
        );
        setDate("");
        setTime("");

        setDescription("");
        setStatus("PENDING");
        setReason("");
        setPet("");
        let user = localStorage.getItem("username");
        setClient(user ? user : "");
        setVet("");
    };

    let dateChangeHandler = (e: ChangeEvent<HTMLInputElement>) => {
        setDate(e.target.value);
    };
    let timeChangeHandler = (e: ChangeEvent<HTMLInputElement>) => {
        setTime(e.target.value);
    };

    let descriptionChangeHandler = (e: ChangeEvent<HTMLInputElement>) => {
        setDescription(e.target.value);
    };
    let reasonChangeHandler = (e: ChangeEvent<HTMLInputElement>) => {
        setReason(e.target.value);
    };

    let petChangeHandler = (e: ChangeEvent<HTMLInputElement>) => {
        setPet(e.target.value);
    };

    let vetChangeHandler = (e: ChangeEvent<HTMLInputElement>) => {
        setVet(e.target.value);
    };

    let registerForm = (
        <>
            <Form onSubmit={registerSubmitHandler}>
                <FormGroup>
                    <Form.Label>Date</Form.Label>
                    <Form.Control
                        type="date"
                        value={date}
                        onChange={dateChangeHandler}
                        placeholder={"dd/mm/aaaa hh:mm"}
                    />
                    <Form.Text className="text-muted"> </Form.Text>
                </FormGroup>
                <FormGroup>
                    <Form.Label>Time</Form.Label>
                    <Form.Control
                        type="time"
                        value={time}
                        onChange={timeChangeHandler}
                        placeholder={"dd/mm/aaaa hh:mm"}
                    />
                    <Form.Text className="text-muted"> </Form.Text>
                </FormGroup>
                <Form.Group controlId="formBasicPassword">
                    <Form.Label>Description</Form.Label>
                    <Form.Control
                        type="text"
                        value={pet}
                        onChange={descriptionChangeHandler}
                    />
                    <Form.Text className="text-muted"></Form.Text>
                </Form.Group>
                <Form.Group controlId="exampleForm.ControlSelect1">
                    <Form.Label>Pet</Form.Label>
                    <Form.Control
                        as="select"
                        value={pet}
                        initialvalue={""}
                        placeholder={""}
                        onChange={petChangeHandler}
                    >
                        <option></option>
                        <FilteredPetSelect />
                    </Form.Control>
                </Form.Group>
                <Form.Group controlId="exampleForm.ControlSelect1">
                    <Form.Label>Vet</Form.Label>
                    <Form.Control as="select" value={vet} onChange={vetChangeHandler}>
                        <option />
                    </Form.Control>
                </Form.Group>
                <button
                    onClick={() => {
                        setRegister(false);
                    }}
                >
                    Book Appointment
                </button>
            </Form>
        </>
    );

    return registerForm;
};

export const ShowRegistration = (props: {
    performAppointmentRegister: (
        date: any,
        time: any,
        description: string,
        status: string,
        reason: string,
        pet: any,
        client: any,
        vet: any
    ) => void;
}) => {
    let [isShowing, setIsShowing] = useState(true);

    return (
        <>
            {isShowing && (
                <ProtoAppointmentRegistration
                    performAppointmentRegister={props.performAppointmentRegister}
                />
            )}
        </>
    );
};

const mapDispatchToProps1 = (dispatch: any) => ({
    performAppointmentRegister: (
        date: any,
        time: any,
        description: string,
        status: string,
        reason: string,
        pet: any,
        client: any,
        vet: any
    ) => {
        dispatch(
            requestAppointmentRegister(
                date,
                time,
                description,
                status,
                reason,
                pet,
                client,
                vet
            )
        );
    }
});

export const AppointmentRegistration = connect(
    null,
    mapDispatchToProps1
)(ShowRegistration);

const ProtoAppointmentView = (props:{apt:Appointment}) =>{

    return <>
        Client: {props.apt.client} <br/>
        Pet: {props.apt.petId} <br/>
        Veterinary: {props.apt.vetId} <br/>
    </>
};

const AptList = (props:{apts:Appointment[]}) =>
    <ul>
        { props.apts.map((apt: Appointment) => <li key={apt.id}><ProtoAppointmentView apt={apt}/></li>)}
    </ul>;

export const ProtoAppointmentList = (props:{apts:Appointment[], loadApts:() => void}) => {
    useEffect(() => props.loadApts(), []);
    console.log(props.apts);
    return <AptList apts={props.apts}/>
};
const mapStateToProps = (state:GlobalState) => ({apts: state.apts.apts});
const mapDispatchToProps = (dispatch:any) => ({loadApts:()=> {dispatch(fetchApts())} });

export const AppointmentList = connect(mapStateToProps, mapDispatchToProps)(ProtoAppointmentList);
