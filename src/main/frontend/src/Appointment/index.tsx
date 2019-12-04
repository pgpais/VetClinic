import React, {ChangeEvent, useEffect, useState} from "react";
import {connect} from "react-redux";
import {GlobalState} from "../App";
import {fetchApts} from "./actions";

export interface Appointment {id:number, clientName:string, petName:string, vetName:string, aptDate:Date}
export interface AptState {apts:Appointment[], isFetchingApts:boolean}

const AptList = (props:{apts:Appointment[]}) =>
    <ul>
        { props.apts.map((apt: Appointment) => <li key={apt.id}><ProtoAppointmentView apt={apt}/></li>)}
    </ul>;

export const ProtoAppointmentList = (props:{apts:Appointment[], loadApts:() => void}) => {
    useEffect(() => props.loadApts(), []);

    return <AptList apts={props.apts}/>
};

const mapStateToProps = (state:GlobalState) => ({apts: state.apts});
const mapDispatchToProps = (dispatch:any) => ({loadApts:()=> {dispatch(fetchApts())} });
export const AppointmentList = connect(mapStateToProps, mapDispatchToProps)(ProtoAppointmentList);

const ProtoAppointmentView = (props:{apt:Appointment}) =>{

    return <>
           Client: {props.apt.clientName} <br/>
           Pet: {props.apt.petName} <br/>
           Veterinary: {props.apt.vetName} <br/>
        </>
};