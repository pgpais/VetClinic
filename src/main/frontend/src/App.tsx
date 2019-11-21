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

// This code is on one file only for demonstration purposes. Modularity should be applied here.

interface Pet { id:number, name:string }

function loadPets(setPets:(pets:Pet[]) => void, filter:string) {
    getData(`/pets?search=${encodeURI(filter)}`, [])
        .then(data => { data && setPets(data.map( (p:{pet:Pet}) => p.pet )) })
    // notice that there is an extra "pet" in the path above which is produced
    // in this particular implementation of the service. {pet: Pet, appointments:List<AppointmentDTO>}
}

const PetList = (props:{pets:Pet[]}) =>
    <ul>
        { props.pets.map((pet:Pet) => <li key={pet.id}>{pet.name}</li>)}
    </ul>;

const App = () => {
  const [ pets, setPets ] = useState([] as Pet[]);
  const [ filter, setFilter ] = useState("");
  const [ isSignedIn, signIn ] = useState(false);
  useEffect(() => loadPets(setPets, filter), [filter]);
  // filter in the deps repeats the search on the server side
  // If the list is empty, the effect is only triggered on component mount


  let handle = (e:ChangeEvent<HTMLInputElement>) => setFilter(e.target.value);
  let filteredList = pets; // pets.filter(p => p.name.includes(filter) ); // << filter on client with this code.

  return (<>
      <SignInForm isSignedIn={isSignedIn} signIn={signIn}/>
      { isSignedIn &&
        <> <PetList pets={filteredList}/>
           <input onChange={handle} value={filter}/>
        </>}
      </>);
};

export default App;
