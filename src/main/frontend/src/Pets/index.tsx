/**
 Copyright 2019 João Costa Seco, Eduardo Geraldo

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

import React, {ChangeEvent, useEffect, useState} from "react";
import {connect} from "react-redux";
import {fetchPets} from "./actions";
import {GlobalState} from "../App";
import "./Pets.css"

export interface Pet { id:number, name:string, photo:string }
export interface PetState { pets: Pet[], isFetching: boolean, isFetchingApts: boolean}

//TODO: create Pet Component to be able to operate it (create appointments and such)

const Pet = (props:{pet:Pet}) =>{

       let [showApt, setShowApt] = useState(false);

    //TODO: create separate folder for a single Pet


    return <>
        <div className={"entityView"} onClick={() => setShowApt(!showApt)}>
            <img src={props.pet.photo} alt={"Pet " + props.pet.name + " photo"}/> <br/>
            Name: {props.pet.name} | ID: {props.pet.id}
        </div>
        </>
};

const PetList = (props:{pets:Pet[]}) =>
    <ul>
        { props.pets.map((pet:Pet) => <li key={pet.id}><Pet pet={pet}/></li>)}
    </ul>;

const ProtoFilteredPetList = (props:{pets:Pet[], username:string,  loadPets:(username:string, filter:string)=>void}) => {
    const [ filter, setFilter ] = useState("");
    let handle = (e:ChangeEvent<HTMLInputElement>) => setFilter(e.target.value);
    // eslint-disable-next-line
    useEffect(() => props.loadPets(props.username, filter), [filter]);

    return <>
             <PetList pets={props.pets}/>
             <input onChange={handle} value={filter}/>
           </>;
};

const mapStateToProps = (state:GlobalState) => ({username:state.signIn.username, pets:state.pets.pets});
const mapDispatchToProps = (dispatch:any) => ({loadPets:(username:string, filter:string) => { dispatch(fetchPets(username, filter))}});
export const FilteredPetList = connect(mapStateToProps,mapDispatchToProps)(ProtoFilteredPetList);
