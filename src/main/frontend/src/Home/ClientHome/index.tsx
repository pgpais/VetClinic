import React, {ChangeEvent, useEffect, useState} from "react";
import {FilteredPetList, PetRegistration} from "../../Pets";
import {Appointment, AppointmentList, ProtoAppointmentList} from "../../Appointment";

const AppointmentForm = () => {

    return <>
        </>
}

const ShowAppointments = () => {

    let [isShowing, setIsShowing] = useState(true);
    let [isCreatingAppointment, setCreatingAppointment] = useState(false);

    return <>
            <button onClick={() => setIsShowing(!isShowing)}>{isShowing? "Hide Appointments" : "Show Appointments"}</button>
            {isShowing && <AppointmentList/>}
            <button onClick={() => setCreatingAppointment(!isCreatingAppointment)}>{isCreatingAppointment? "Cancel":"Create Appointment"} </button>
            {isCreatingAppointment && <AppointmentForm/>}
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