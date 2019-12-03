import {Pet} from "../Pets";
import {getData} from "../Utils/NetworkUtils";


export const REQUEST_APTS_BY_PET = "REQUEST_APTS_BY_PET";

export const requestAptsByPet = () => ({type:REQUEST_APTS_BY_PET});