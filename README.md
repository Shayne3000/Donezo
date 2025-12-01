# Donezo

A triage of building production-ready, cross-platform apps on Android & iOS devices using Kotlin Multiplatform. It employs the following multiplatform technologies and paradigms:

- Compose Multiplatform for UI,
- Navigation-Compose for navigation,
- [Shimmer](https://github.com/valentinilk/compose-shimmer) for constructing UI loading placeholders,
- [Compose Multiplatorm ScalableDp/Sp](https://github.com/Chaintech-Network/sdp-ssp-compose-multiplatform) for dimension scaling based on screen density,
- Coil for image loading,
- Koin for Dependency Injection,
- Kotlin Serialization for JSON parsing,
- Ktor for networking,
- Room for local persistence,
- Kotlin Date/Time for date and time management.
- Kotlin Coroutines for Concurrency,
- Kotlin Flows for observable data streams, and
- A layered architectural paradigm.

## App Screenshots

<details>
<summary><H3>Android</H3></summary>
<table>
  <tr>
    <th rowspan="2">Screen</th>
    <th colspan="2">Theme</th>
  </tr>
  <tr>
    <th>Light</th>
    <th>Dark</th>
  </tr>
  <tr>
    <td><b>To-do tasks Tab</b></td>
    <td><img width="300" height="680" src="https://github.com/user-attachments/assets/7d845050-9f0a-436a-9df6-e3d840db25dd"/></td>
    <td><img width="300" height="680" src="https://github.com/user-attachments/assets/503b17ae-0967-4401-a6f0-54a9c85f6be6"/></td>
  </tr>
  <tr>
    <td><b>Completed tasks Tab</b></td>
    <td><img width="300" height="680" src="https://github.com/user-attachments/assets/048f8104-2c7a-44f6-9e52-9f70f59e047c"/></td>
    <td><img width="300" height="680" src="https://github.com/user-attachments/assets/34bbc30b-517b-47e5-ae21-79eb91cd6974"/></td>
  </tr>
  <tr>
    <td><b>Characters Tab</b></td>
    <td><img width="300" height="680" src="https://github.com/user-attachments/assets/8de9067e-bc95-42f5-a941-17fa6d4d00e3"/></td>
    <td><img width="300" height="680" src="https://github.com/user-attachments/assets/e36e5299-f672-4bad-97e7-d14f89e16397"/></td>
  </tr>
  <tr>
    <td><b>Create new task Dialog</b></td>
    <td><img width="300" height="680" src="https://github.com/user-attachments/assets/963e3d2b-3446-4f5f-ae08-78a2d5255bf9"/></td>
    <td><img width="300" height="680" src="https://github.com/user-attachments/assets/2019a871-1722-4397-b6a0-a1dd3c9f770b"/></td>
  </tr>
  <tr>
    <td><b>View task Dialog</b></td>
    <td><img width="300" height="680" src="https://github.com/user-attachments/assets/0e43bb7c-e8d2-4ed6-bf7c-d8413d75a0b6"/></td>
    <td><img width="300" height="680" src="https://github.com/user-attachments/assets/c6aa1093-6996-49bd-8915-088f363c41c3"/></td>
  </tr>
    <tr>
    <td><b>Edit task Dialog</b></td>
    <td><img width="300" height="680" src="https://github.com/user-attachments/assets/b0c43053-b0fa-48d4-90a5-e5f8b3cfdfaf"/></td>
    <td><img width="300" height="680" src="https://github.com/user-attachments/assets/6f019f0f-08a8-4278-a84d-e2709135467d"/></td>
  </tr>
</table>
</details>

<details>
<summary><H3>iOS</H3></summary>
<table>
  <tr>
    <th rowspan="2">Screen</th>
    <th colspan="2">Theme</th>
  </tr>
  <tr>
    <th>Light</th>
    <th>Dark</th>
  </tr>
  <tr>
    <td><b>To-do tasks Tab</b></td>
    <td><img width="300" height="680" src="https://github.com/user-attachments/assets/98e5f2da-a70b-4dc0-8db4-63ac4ede9a42"/></td>
    <td><img width="300" height="680" src="https://github.com/user-attachments/assets/8e777df9-b5ad-4deb-b0fe-4fa42775cdcd"/></td>
  </tr>
  <tr>
    <td><b>Completed tasks Tab</b></td>
    <td><img width="300" height="680" src="https://github.com/user-attachments/assets/49ed81c5-acf6-48b8-b4c3-93dbb04ccdaf"/></td>
    <td><img width="300" height="680" src="https://github.com/user-attachments/assets/a67ef960-43ff-4901-8b2d-06161e3a3468"/></td>
  </tr>
  <tr>
    <td><b>Characters Tab</b></td>
    <td><img width="300" height="680" src="https://github.com/user-attachments/assets/dddbdb0b-27c1-4f03-9b37-939bf77722d0"/></td>
    <td><img width="300" height="680" src="https://github.com/user-attachments/assets/4e743030-4a17-4111-92b9-630f77977dac"/></td>
  </tr>
  <tr>
    <td><b>Create new task Dialog</b></td>
    <td><img width="300" height="680" src="https://github.com/user-attachments/assets/8866832f-e40a-41c6-893b-31c5c7be5af7"/></td>
    <td><img width="300" height="680" src="https://github.com/user-attachments/assets/4db3547d-16a8-4100-a102-ba23389fddc9"/></td>
  </tr>
  <tr>
    <td><b>View task Dialog</b></td>
    <td><img width="300" height="680" src="https://github.com/user-attachments/assets/a5605443-c5e7-4434-bbf2-422b36f0be86"/></td>
    <td><img width="300" height="680" src="https://github.com/user-attachments/assets/b8a7b0d8-3acd-461f-89c2-b15dbed8134c"/></td>
  </tr>
    <tr>
    <td><b>Edit task Dialog</b></td>
    <td><img width="300" height="680" src="https://github.com/user-attachments/assets/5747524b-b318-4c6f-903c-5d82a1d60e6b"/></td>
    <td><img width="300" height="680" src="https://github.com/user-attachments/assets/fd46dd22-c1e0-46f2-9d03-70af59b48131"/></td>
  </tr>
</table>
</details>
