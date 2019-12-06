import { Action } from "redux";
import { AptState, PendingAptState } from "./index";
import {
  ADD_APT,
  RECEIVE_APTS,
  ReceiveAptsAction,
  REQUEST_APTS,
  REQUEST_PENDING_APTS,
  RECEIVE_PENDING_APTS,
  ReceivePendingAptsAction
} from "./actions";

const initialState = {
  apts: [],
  isFetchingApts: false,
  pending_apts: []
};

const aa = {
  isFetchingApts: false
};

export function aptReducer(
  state: AptState = initialState,
  action: Action
): AptState {
  switch (action.type) {
    case ADD_APT:
      return { ...state }; //TODO: change state to add new added Apt
    case REQUEST_APTS:
      return { ...state, isFetchingApts: true };
    case RECEIVE_APTS:
      return {
        ...state,
        isFetchingApts: false,
        apts: (action as ReceiveAptsAction).data
      };
    case REQUEST_PENDING_APTS:
      return { ...state, isFetchingApts: true };
    case RECEIVE_PENDING_APTS:
      return {
        ...state,
        isFetchingApts: false,
        pending_apts: (action as ReceivePendingAptsAction).data
      };

    default:
      return state;
  }
}

// export function a(
//   state: PendingAptState = aa,
//   action: Action
// ): PendingAptState {
//   switch (action.type) {
//     case REQUEST_PENDING_APTS:
//       return { ...state, isFetchingApts: true };
//     case RECEIVE_PENDING_APTS:
//       return {
//         ...state,
//         isFetchingApts: false,
//         pending_apts: (action as ReceivePendingAptsAction).data
//       };
//     default:
//       return state;
//   }
// }

export default aptReducer;
