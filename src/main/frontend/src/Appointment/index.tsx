import React, {ChangeEvent, useEffect, useState} from "react";
import {connect} from "react-redux";
import {Pet} from "../Pets";
import {Vet} from "../Home";

export interface Appointment{aptDate:Date, clientName:string, pet:Pet, vet:Vet}

const ProtoAppointmentForm = () =>
    <form>
    </form>;

const ProtoAppointment = (props:{apt:Appointment}) =>
        <>
            Appointment Date: {props.apt.aptDate} <br/>
            {props.apt.clientName}'s {props.apt.pet.name} with Dr. {props.apt.vet.name}
        </>;

export default ProtoAppointment