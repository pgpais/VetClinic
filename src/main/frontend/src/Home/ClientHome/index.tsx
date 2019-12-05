import React, {ChangeEvent, useEffect, useState, FormEvent} from "react";
import {FilteredPetList, PetRegistration} from "../../Pets";
import {Appointment, AppointmentList, ProtoAppointmentList} from "../../Appointment";
import { connect } from "react-redux";
import { requestAppointmentRegister } from "../../Appointment/actions";
import Form from "react-bootstrap/Form";
import FormGroup from "react-bootstrap/FormGroup";


    
    const ProtoAppointmentRegistration = (
        props: {
            performAppointmentRegister: (
                date: any,
                time:any,
                description: string,
                status: string,
                reason: string,
                pet: any,
                client: any,
                vet: any
            ) => void;
        }
    ) => {
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
                            placeholder ={"dd/mm/aaaa hh:mm"}
                        />
                        <Form.Text className="text-muted"> </Form.Text>
                    </FormGroup>;

                 
                        <FormGroup>
                            <Form.Label>Time</Form.Label>
                            <Form.Control
                                type="time"
                                value={time}
                                onChange={timeChangeHandler}
                                placeholder={"dd/mm/aaaa hh:mm"}
                            />
                            <Form.Text className="text-muted"> </Form.Text>
                        </FormGroup>;
    
                       
                    <Form.Group controlId="formBasicPassword">
                        <Form.Label>Description</Form.Label>
                        <Form.Control
                            type="text"
                            value={description}
                            onChange={descriptionChangeHandler}
                        />
                        <Form.Text className="text-muted"></Form.Text>
                    </Form.Group>
                  
                    <Form.Group controlId="exampleForm.ControlSelect1">
                        <Form.Label>Pet</Form.Label>
                        <Form.Control as="select" value={pet}
                            onChange={petChangeHandler}>
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                            <option>4</option>
                            <option>5</option>
                        </Form.Control>
                    </Form.Group>
    
                    <Form.Group controlId="exampleForm.ControlSelect1">
                        <Form.Label>Vet</Form.Label>
                        <Form.Control as="select" value={vet}
                            onChange={vetChangeHandler}>
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                            <option>4</option>
                            <option>5</option>
                        </Form.Control>
                    </Form.Group>
                    
                    <button onClick={() => setRegister(false)}>Book Appointment</button>
                </Form>
            </>
        );

        return registerForm;
    };




const ShowRegistration = (props: {
    performAppointmentRegister: (
        date: any,
        time:any,
        description: string,
        status: string,
        reason: string,
        pet: any,
        client: any,
        vet: any
    ) => void;
}) => {
    let [isShowing, setIsShowing] = useState(false);

    return (
        <>
            {isShowing && <ProtoAppointmentRegistration performAppointmentRegister={props.performAppointmentRegister} />}
            <button onClick={() => setIsShowing(!isShowing)}>
                {isShowing ? "Cancel Booking" : "Book Appointment"}
            </button>
        </>
    );
};

const mapDispatchToProps1 = (dispatch: any) => ({
    performAppointmentRegister: (
        date: any,
        time:any,
        description: string,
        status: string,
        reason: string,
        pet: any,
        client: any,
        vet: any
    ) => {
        dispatch(requestAppointmentRegister(
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

export const AppointmentRegistration = connect(null,
    mapDispatchToProps1
)(ShowRegistration);

    

const ShowAppointments = () => {

    let [isShowing, setIsShowing] = useState(true);
    let [isCreatingAppointment, setCreatingAppointment] = useState(false);

    return <>
            <button onClick={() => setIsShowing(!isShowing)}>{isShowing? "Hide Appointments" : "Show Appointments"}</button>
            {isShowing && <AppointmentList/>}
            <button onClick={() => setCreatingAppointment(!isCreatingAppointment)}>{isCreatingAppointment? "Cancel":"Create Appointment"} </button>
        {isCreatingAppointment && <AppointmentRegistration/>}
        </>
};

const ProtoClientHome = () => {


    return <>
        <FilteredPetList/>
        <PetRegistration/>
        <ShowAppointments/>
    </>;
};


export default ProtoClientHome;