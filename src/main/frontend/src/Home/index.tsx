import React, { ChangeEvent, useEffect, useState } from "react";
import { connect } from "react-redux";
import { fetchVets } from "./actions";
import { GlobalState } from "../App";
import ProtoClientHome from "./ClientHome";
import ProtoAdminHome from "./AdminHome";
import ProtoVetHome from "./VetHome";

export interface Vet {
  vetId: number;
  name: string;
}
export interface VetState {
  vets: Vet[];
  isFetching: boolean;
}

// Add a Vet or User component here. so it is clickable to find more information on this user
const VetList = (props: { vets: Vet[]; loadVets: () => void }) => {
  useEffect(() => props.loadVets(), []);

  return (
    <ul>
      {props.vets.map((vet: Vet) => (
        <li key={vet.vetId}>{vet.name}</li>
      ))}
    </ul>
  );
};

const ProtoVetList = (props: {
  vets: Vet[];
  loadVets: () => void;
  isSignedIn: boolean;
}) => {
  //TODO: change name to ProtoHome?
  const [viewingVets, setViewingVets] = useState(false);

  let role = localStorage.getItem("role");

  return (
    <>
      {role == "ROLE_CLIENT" && <ProtoClientHome />}
      {role == "ROLE_ADMIN" && <ProtoAdminHome />}
      {role == "ROLE_VET" && <ProtoVetHome />}
      {viewingVets && <VetList vets={props.vets} loadVets={props.loadVets} />}
      {viewingVets ? (
        <button onClick={() => setViewingVets(false)}> Hide Vets </button>
      ) : (
        <button onClick={() => setViewingVets(true)}>View all Vets</button>
      )}
    </>
  );
};

const mapStateToProps = (state: GlobalState) => ({
  vets: state.vets.vets,
  isSignedIn: state.signIn.isSignedIn
});
const mapDispatchToProps = (dispatch: any) => ({
  loadVets: () => {
    dispatch(fetchVets());
  }
});
export const Home = connect(mapStateToProps, mapDispatchToProps)(ProtoVetList);
