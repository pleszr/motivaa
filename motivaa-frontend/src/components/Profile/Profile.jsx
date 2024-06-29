import TextInput from "../Inputs/TextInput";
import roland from '../../assets/roland.png';
import Button from "../Button";
import { useKeycloak } from '@react-keycloak/web';
import { useState } from "react";


export default function Profile({ edit, setEdit }) {
    const { keycloak, initialized } = useKeycloak();
    const [isDisabled, setIsDisabled] = useState(true);

    const handleEdit = () => {
        setEdit(prev => !prev);
        setIsDisabled(prev => !prev);
    }
    return (
        <section className="w-full pb-12 flex flex-col justify-center items-center gap-8">
            <h2 className="uppercase text-2xl">Profile</h2>
            <div className="w-4/5 mx-auto flex justify-center items-start gap-12">
                <div className="w-fit self-center">
                    <img className="max-w-80" src={roland} alt="profile picture" />
                </div>
                <div className="w-2/3 bg-white p-8 rounded-2xl shadow-motivaa-shadow flex flex-col justify-start items-stretch gap-8">
                    <div className="flex justify-between items-start gap-20">
                        <div className="w-full flex flex-col gap-4">
                            <TextInput isReadonly={edit} text="First Name" value="Roland" />
                            <TextInput isReadonly={edit} text="Last Name" value="Plesz" />
                            <TextInput isReadonly={edit} isDisabled={isDisabled} text="Email" value="roland.plesz@gmail.com" />
                        </div>
                        <div className="pt-10 flex flex-col gap-4 w-fit">
                            <Button handleClick={() => {initialized && keycloak.logout()}} text={<i className="fa-solid fa-arrow-right-from-bracket"></i>} style="bg-red text-2xl w-30" />
                            <Button handleClick={handleEdit} text={edit ? <i className="fa-solid fa-pen"></i> : <i className="fa-solid fa-check"></i>} style="text-2xl w-30" />
                        </div>
                    </div>
                    <Button text="Reset Password" style="w-fit self-center" />
                </div>
            </div>
        </section>
    )
}