import axios from "axios";
import React, { useEffect, useState } from "react";
import { useModal } from "react-hooks-use-modal";

const LocationsTable = () => {
  const [locationsTable, setLocationsTable] = useState([]);
  const [catapi, setCatApi] = useState([]);
  useEffect(() => {
    const fetchLocations = () => {
      axios.get("http://localhost:8080/getLocations").then((response) => {
        setLocationsTable(response.data);
      });
      axios.get("http://localhost:8080/getCategories").then((response) => {
        setCatApi(response.data);
      });
    };

    fetchLocations();
  }, []);

  const insertLocation = async () => {
    try {
      const newLocation = {
        name: name,
        description: desc,
        city: city,
        image: image,
        categories: selectedCategories,
      };
      console.log(JSON.stringify(newLocation));

      const response = await axios.post(
        "http://localhost:8080/insertLocation",
        newLocation
      );
      window.location.reload();
    } catch (error) {
      console.error(error);
    }
  };

  const [name, setName] = useState("");
  const [desc, setDesc] = useState("");
  const [city, setCity] = useState("");
  const [image, setImage] = useState("");
  const [selectedCategories, setSelectedCategories] = useState([]);
  const [Modal, open, close, isOpen] = useModal("root", {
    preventScroll: true,
    closeOnOverlayClick: false,
  });
  const handleCategorySelection = (e) => {
    const selectedOptions = Array.from(e.target.selectedOptions, (option) => {
      const [id, name] = option.value.split("-");
      return { id: Number(id), name: name };
    });
    setSelectedCategories(selectedOptions);
  };

  const handleDelete = (id) => {
    axios
      .delete(`http://localhost:8080/deleteLocation/${id}`)
      .then((response) => {
        console.log(response);
      });
  };
  const removeCategory = (cat) => {
    const updatedCategories = selectedCategories.filter((c) => c !== cat);
    setSelectedCategories(updatedCategories);
  };
  return (
    <div className="flex h-screen p-22 justify-center items-center">
      <div class="relative overflow-x-auto shadow-md sm:rounded-lg">
        <button
          onClick={open}
          className="text-white bg-gradient-to-r from-green-400 via-green-500 to-green-600 hover:bg-gradient-to-br focus:ring-4 focus:outline-none focus:ring-green-300 dark:focus:ring-green-800 font-medium rounded-lg text-sm px-5 py-2.5 text-center mr-2 mb-2"
        >
          Insert Location
        </button>
        <table class="w-full text-sm text-left text-gray-500 dark:text-gray-400">
          <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
            <tr>
              <th scope="col" class="px-6 py-3">
                Name
              </th>
              <th scope="col" class="px-6 py-3">
                Description
              </th>
              <th scope="col" class="px-6 py-3">
                City
              </th>
              <th scope="col" class="px-6 py-3">
                Image
              </th>
              <th scope="col" class="px-6 py-3">
                Category
              </th>
              <th scope="col" class="px-6 py-3">
                <span class="sr-only">Edit</span>
              </th>
            </tr>
          </thead>
          <tbody>
            {locationsTable.map((user, i) => {
              return (
                <tr
                  key={i}
                  class="bg-white dark:bg-gray-800 hover:bg-gray-50 dark:hover:bg-gray-600"
                >
                  <th
                    scope="row"
                    class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white"
                  >
                    {user.name}
                  </th>
                  <td class="px-6 py-4">{user.description}</td>
                  <td class="px-6 py-4">{user.city}</td>
                  <td class="px-6 py-4">
                    <img
                      src={user.image.replace(
                        "fakepath",
                        "Users\\px\\Downloads"
                      )}
                    ></img>
                  </td>

                  <td class="px-6 py-4">
                    {user.categories.map((cat) => {
                      return <h1>{cat.name}</h1>;
                    })}
                  </td>
                  <td class="px-6 py-4 text-right">
                    <button
                      onClick={() => {
                        handleDelete(user.id);
                      }}
                      class="font-medium text-blue-600 dark:text-blue-500 hover:underline"
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              );
            })}
          </tbody>
        </table>
      </div>
      <Modal>
        <div className="bg-white p-14 rounded-xl">
          <div class="flex items-start justify-between p-5 border-b rounded-t dark:border-gray-700">
            <h3 class="text-xl font-semibold dark:text-white">
              Add New Location
            </h3>
            <button
              type="button"
              class="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center dark:hover:bg-gray-700 dark:hover:text-white"
              data-modal-toggle="add-user-modal"
              onClick={close}
            >
              <svg
                class="w-5 h-5"
                fill="currentColor"
                viewBox="0 0 20 20"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path
                  fill-rule="evenodd"
                  d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                  clip-rule="evenodd"
                ></path>
              </svg>
            </button>
          </div>

          <div class="p-6 space-y-6">
            <form action="#">
              <div class="grid grid-cols-6 gap-6">
                <div class="col-span-6 sm:col-span-3">
                  <label
                    for="name"
                    class="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                  >
                    Name
                  </label>
                  <input
                    type="text"
                    name="name"
                    id="first-name"
                    class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500"
                    placeholder="Name"
                    required=""
                    onChange={(e) => {
                      setName(e.target.value);
                    }}
                  />
                </div>
                <div class="col-span-6 sm:col-span-3">
                  <label
                    for="email"
                    class="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                  >
                    Description
                  </label>
                  <input
                    type="text"
                    name="email"
                    id="last-name"
                    class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500"
                    placeholder="Description"
                    required=""
                    onChange={(e) => {
                      setDesc(e.target.value);
                    }}
                  />
                </div>
                <div class="col-span-6 sm:col-span-3">
                  <label
                    for="email"
                    class="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                  >
                    City
                  </label>
                  <input
                    type="text"
                    name="city"
                    id="city"
                    class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500"
                    placeholder="Password"
                    required=""
                    onChange={(e) => {
                      setCity(e.target.value);
                    }}
                  />
                </div>
                <div class="col-span-6 sm:col-span-3">
                  <label
                    for="position"
                    class="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                  >
                    Image
                  </label>
                  <input
                    type="file"
                    name="password"
                    id="position"
                    class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500"
                    placeholder="image"
                    required=""
                    onChange={(e) => {
                      setImage(e.target.value);
                    }}
                  />
                </div>
                <div className="col-span-6 sm:col-span-3">
                  <label
                    htmlFor="position"
                    className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                  >
                    Categories
                  </label>
                  <select
                    multiple
                    value={selectedCategories.map((cat) => cat.id)} // Extract the IDs from the selected categories
                    onChange={handleCategorySelection}
                    className="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500"
                  >
                    {catapi?.map((cat) => (
                      <option key={cat.id} value={`${cat.id}-${cat.name}`}>
                        {cat.id}-{cat.name}
                      </option>
                    ))}
                  </select>
                  {selectedCategories.map((cat) => (
                    <div className="flex flex-row bg-gray-300 rounded-lg m-1 justify-around items-center">
                      <h3 className="bg-green-400 text-gray-200 rounded-xl p-2 m-2 w-14">
                        {cat.name}
                      </h3>
                      <button
                        onClick={() => removeCategory(cat)}
                        className="w-10 h-auto  bg-gray-200 m-2 text-black rounded-full"
                      >
                        X
                      </button>
                    </div>
                  ))}
                </div>
              </div>
            </form>
          </div>

          <div class="items-center p-6 border-t border-gray-200 rounded-b dark:border-gray-700">
            <button
              className="text-white bg-gradient-to-r from-green-400 via-green-500 to-green-600 hover:bg-gradient-to-br focus:ring-4 focus:outline-none focus:ring-green-300 dark:focus:ring-green-800 font-medium rounded-lg text-sm px-5 py-2.5 text-center mr-2 mb-2"
              onClick={() => {
                insertLocation();
              }}
            >
              New Location
            </button>
          </div>
        </div>
      </Modal>
    </div>
  );
};

export default LocationsTable;
