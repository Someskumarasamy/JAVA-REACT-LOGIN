import React from 'react'
import Cookies from 'js-cookie'
import { api, googleId } from './Api'
import { GoogleLogout } from 'react-google-login';

function Profile() {
    const [name, setname] = React.useState('');
    const [ppic, setpic] = React.useState('');
    const [role, setrole] = React.useState('');
    const [roleid, setroleid] = React.useState(0);
    React.useEffect(() => {
        if (Cookies.get("username")) {
            setname(Cookies.get("username"));
        } else {
            setname(localStorage.getItem("username"));
        }
        if (Cookies.get("userImage")) {
            setpic(Cookies.get("userImage"));
        } else {
            setpic(localStorage.getItem("userImage"));
        }
        api.get(`?oper-type=getuserrole`)
            .then((response) => {
                console.log(response.data)
                return response.data;
            })
            .then(dta => {
                setrole(dta.roleName);
                setroleid(dta.roleId);
            })
            .catch(err => {
                console.log(err)
            })
    }, [])
    const logout = () => {
        api.get(`?oper-type=logout`)
            .then(response => {
                localStorage.clear()
                Cookies.remove("username");
                Cookies.remove("userImage");
                window.location.href = "http://localhost:8080/SimpleLogin/"
            })

            .catch(err => { })
    }
    const mainChangeRole = (rle) => {
        api.post(`?oper-type=changerole&roleId=${rle}`)
            .then(response => {
                var dta = response.data;
                setrole(dta.roleName);
                setroleid(dta.roleId);
            })

            .catch(err => { })
    }
    const changeRole = () => {
        var id = 0;
        if (roleid === '1') {
            id = 2;
        } else if (roleid === '2') {
            id = 1;
        }
        mainChangeRole(id);
    }
    return (
        <div>
            <h1>{`Welcome ${role}, ${name}`}</h1>
            <img src={ppic} alt="goole profile"></img>
            <button onClick={changeRole}>Switch Role {roleid}</button>
            <GoogleLogout
                clientId={googleId}
                buttonText="Logout"
                onLogoutSuccess={res => {
                    logout(res)
                }}
            >
            </GoogleLogout>
        </div>
    )
}

export default Profile
