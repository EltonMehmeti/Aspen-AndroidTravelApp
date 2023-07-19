import React, { useState } from "react";
import axios from "axios";
import UsersTable from "./components/UsersTable";
import LocationsTable from "./components/LocationsTable";

function App() {
  return (
    <>
      <UsersTable />
      <LocationsTable />
    </>
  );
}

export default App;
