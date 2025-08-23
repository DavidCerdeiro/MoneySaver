import '@/styles/utilities.css';

import { Card, CardContent, CardFooter, CardHeader, CardTitle, CardDescription } from  "@/app/domains/shared/components/card";
import { Button } from "@/app/domains/shared/components/button";
import { InputOTP, InputOTPGroup, InputOTPSlot } from "@/app/domains/shared/components/input-otp.tsx";
import { useTranslation } from 'react-i18next';
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { createEmailVerificationSchema } from "../schemas/EmailVerification";
import type { EmailVerificationData } from "../schemas/EmailVerification";
import { emailVerification, authUser } from '../application/AuthService';
import { useState } from 'react';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { toast } from "sonner";
import { deleteProfile } from '../../user/application/UserService';
import { useUser } from '@/app/contexts/UserContext';

export function VerificationCodeForm({ source }: { source: "login" | "signup" | "forgot" | "delete-profile" }) {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const schema = createEmailVerificationSchema(t);
    const { i18n } = useTranslation();
    const { checkAuth } = useUser();

    let email = sessionStorage.getItem('email');
    let title = t('auth.forgotTitle');
    let description = "";
    let submit = "";

    //Depending on the source, we set the title, description and submit button text.
    switch (source) {
        case "login":
            description = t('auth.loginDescription');
            submit = t('auth.loginSubmit');
            break;
        case "signup":
            description = t('auth.signUpDescription');
            submit = t('auth.signUpSubmit');
            break;
        case "forgot":
            description = t('auth.forgotDescription');
            submit = t('auth.forgotSubmit');
            break;
        case "delete-profile":
            title = t('domains.user.delete.confirm.title');
            description = t('domains.user.delete.confirm.description');
            submit = t('domains.user.delete.confirm.submit');
            break;
    }
    
    const [otpValue, setOtpValue] = useState("");

    const {
        register,
        handleSubmit,
        setValue,
        formState: { isSubmitting },

        } = useForm<EmailVerificationData>({
        resolver: zodResolver(schema),
    });
    
    useEffect(() => {
        register("code");
        
    }, [register, email]);

    useEffect(() => {
        register("email");
        setValue("email", email || "");
    }, [register, email]);

    const onChangeOTP = (val: string) => {
        setOtpValue(val);
        setValue("code", val);
    };

    const onSubmit = async (data: EmailVerificationData) => {
        try {
            sessionStorage.removeItem('email');
            const languageTag = i18n.language || "en";
            const [language, country = ""] = languageTag.split("-");
            const requestBody: any = {
                code: data.code,
                locale: `${language}_${country}`,
            };

            // Include email in the request body for non-delete sources
            if (source !== "delete-profile") {
                requestBody.email = data.email;
            }

            if(source === "forgot") {
                await emailVerification(requestBody);

                localStorage.setItem('otpEmail', data.email ? data.email : "");
                navigate("/forgot-password/reset-password");
            }else if(source === "signup") {
                await authUser(requestBody);
                
                toast.success(t('auth.signUpSuccess'));
                await checkAuth();
                navigate("/home");
            }else if(source === "login") {
                await emailVerification(requestBody);

                toast.success(t('auth.loginSuccess'));
                await checkAuth();
                navigate("/home");
            }else if(source === "delete-profile") {
                await deleteProfile(requestBody);

                toast.success(t('domains.user.delete.success'));
                navigate("/");
            }
        } catch (error) {
            toast.error(t('auth.verificationError'));
        }
    };
    return (
            <Card className="form-card">
                <CardHeader>
                    <CardTitle className="text-2xl ">{title}</CardTitle>
                    <CardDescription className="card-description">{description}</CardDescription>
                </CardHeader>
                <CardContent className="grid gap-4">
                    <form onSubmit={handleSubmit(onSubmit)} className="grid gap-4 md:gap-6">
                        <div className="flex justify-center">
                            <InputOTP
                                maxLength={6}
                                onChange={onChangeOTP}
                                value={otpValue}
                                containerClassName="justify-center"
                            >
                                <InputOTPGroup>
                                <InputOTPSlot index={0} />
                                <InputOTPSlot index={1} />
                                <InputOTPSlot index={2} />
                                <InputOTPSlot index={3} />
                                <InputOTPSlot index={4} />
                                <InputOTPSlot index={5} />
                                </InputOTPGroup>
                            </InputOTP>
                        </div>
                            <Button type="submit" className="button-green" disabled={isSubmitting}>
                            {submit}
                            </Button>
                    </form>
                </CardContent>
                <CardFooter>
                </CardFooter>
            </Card>
    )
}

