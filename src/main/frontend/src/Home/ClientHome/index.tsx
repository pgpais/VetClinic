import React, {ChangeEvent, useEffect, useState} from "react";
import {FilteredPetList, PetRegistration} from "../../Pets";
import {Appointment, AppointmentList, ProtoAppointmentList} from "../../Appointment";



const ShowAppointments = () => {

    let [isShowing, setIsShowing] = useState(true);

    return <>
            <button onClick={() => setIsShowing(!isShowing)}>{isShowing? "Hide Appointments" : "Show Appointments"}</button>
            {isShowing && <AppointmentList/>}
        </>
};

const ProtoClientHome = () => {


    return <>
        <ShowAppointments/>
        <FilteredPetList/>
        <PetRegistration/>
    </>;
};


export default ProtoClientHome;