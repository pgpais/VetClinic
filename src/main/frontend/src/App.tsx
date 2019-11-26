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

import React, {ChangeEvent, useEffect, useState} from 'react';
import './App.css';
import SignInForm, { getData } from "./SignIn";
import RegisterForm from "./Register";

// This code is on one file only for demonstration purposes. Modularity should be applied here.

interface Pet { id:number, name:string }

function loadPets(setPets:(pets:Pet[]) => void, filter:string) {
    console.log("LOADPETS")
    getData(`/pets?search=${encodeURI(filter)}`, [])
        .then(data => { data && setPets(data.map( (p:{pet:Pet}) => p.pet )) })
    // notice that there is an extra "pet" in the path above which is produced
    // in this particular implementation of the service. {pet: Pet, appointments:List<AppointmentDTO>}
}

const PetList = (props:{pets:Pet[], setPets:(p:Pet[])=>void, filter:string}) =>{

    useEffect(() => loadPets(props.setPets, props.filter), [props.filter]);
    // filter in the deps repeats the search on the server side
    // If the list is empty, the effect is only triggered on component mount

    return (<ul>
        { props.pets.map((pet:Pet) => <li key={pet.id}>{pet.name}</li>)}
    </ul>)
};

const App = () => {
  const [ pets, setPets ] = useState([] as Pet[]);
  const [ filter, setFilter ] = useState("");
  const [ isSignedIn, signIn ] = useState(false);



  let handle = (e:ChangeEvent<HTMLInputElement>) => setFilter(e.target.value);
  let filteredList = pets; // pets.filter(p => p.name.includes(filter) ); // << filter on client with this code.

  /*return (<>
      <RegisterForm isSignedIn={false}/>
      </>);
    */
  return (<>
        <SignInForm isSignedIn={isSignedIn} signIn={signIn}/>
        { isSignedIn &&
          <> <PetList pets={filteredList} setPets={setPets} filter={filter}/>
             <input onChange={handle} value={filter}/>
          </>}
        </>);
};

export default App;
