import React from 'react'
import { Redirect } from 'react-router-dom'
import { api } from './Api'

function ChangeRole() {
    const [succ, setsucc] = React.useState(false);
    const setUserRole = () => {
        var rle = 2;
        if (document.getElementById('adm').checked) {
            rle = 1;
        }
        api.post(`?oper-type=changerole&roleId=${rle}`)
            .then(response => {
                console.log(response.data)
                return response.data.roleName;
            })
            .then(dta => {
                setsucc(true);
            })
            .catch(err => { setsucc(false); })
    }
    const loadView = () => {
        if (!succ) {
            return (
                <div>
                    Choose role<br />
                    <input type="radio" name="roles" id="adm" value="seller"></input>
                    <label htmlFor="adm">Seller</label><br />
                    <input type="radio" name="roles" id="usr" value="user" checked></input>
                    <label htmlFor="adm">User</label><br />
                    <button onClick={setUserRole}>Next</button>
                </div>
            )
        } else {
            return <Redirect to="/SimpleLogin/profile" />
        }
    }
    return (
        loadView()
    )
}

export default ChangeRole
