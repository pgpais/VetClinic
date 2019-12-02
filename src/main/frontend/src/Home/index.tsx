import React, {ChangeEvent, useEffect, useState} from "react";
import {connect} from "react-redux";
import {fetchVets} from "./actions";
import {GlobalState} from "../App";

export interface Vet {vetId:number, name:string}
export interface VetState { vets:Vet[], isFetching:boolean}

// Add a Vet or User component here. so it is clickable to find more information on this user
const VetList = (props:{vets:Vet[]}) =>
    <ul>
        { props.vets.map((vet:Vet) => <li key={vet.vetId}>{vet.name}</li>)}
    </ul>;

const ProtoVetList = (props:{vets:Vet[], loadVets:()=>void}) =>{

    useEffect(() => props.loadVets(), []);

    return  <>
                <VetList vets={props.vets}/>
            </>
};

const mapStateToProps = (state:GlobalState) => ({vets:state.vets.vets});
const mapDispatchToProps = (dispatch:any) => ({loadVets:() => { dispatch(fetchVets())}});
export const Home = connect(mapStateToProps,mapDispatchToProps)(ProtoVetList);