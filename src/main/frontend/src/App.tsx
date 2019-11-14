import React, { useState, ChangeEvent, useEffect } from "react";
import "./App.css";

interface Pet {
  pet: {
    name: string;
    id: number;
  };
}

const PetList = (props: { pets: Pet[] }) => {
  return (
    <ul>
      {props.pets.map(p => (
        <li>{p.pet.name}</li>
      ))}
    </ul>
  );
};

function loadPets(setPets: (pets: Pet[]) => void) {
  fetch("/pets")
    .then(response => {
      console.log("Got this:");
      return response.json();
    })
    .then(data => {
      console.log(data);
      return setPets(data);
    })
    .catch(err => console.log(err));
}

const App = () => {
  const [filter, setFilter] = useState("");

  const [pets, setPets] = useState([] as Pet[]);

  useEffect(() => loadPets(setPets), []);

  const filteredPets = pets.filter(p => p.pet.name.includes(filter));

  const handler = (e: ChangeEvent<HTMLInputElement>) => {
    setFilter(e.target.value);
  };

  return (
    <>
      <PetList pets={filteredPets} />{" "}
      <label>
        Filter
        <input type="text" value={filter} onChange={handler}></input>
      </label>
    </>
  );
};

export default App;
