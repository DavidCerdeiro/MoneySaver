import '@/styles/utilities.css';

import { Card, CardContent, CardFooter, CardHeader, CardTitle, CardDescription } from "@/app/domains/shared/components/card";
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
import { useUser } from "@/app/contexts/UserContext";
import { toast } from "sonner";

export function VerificationCodeForm({ source }: { source: "login" | "signup" | "forgot" }) {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const schema = createEmailVerificationSchema(t);
    const { user } = useUser();
    const { i18n } = useTranslation();

    let email = "";
    let title = "";
    let description = "";
    let submit = "";
    //Depending on the source, we set the title, description, submit button text, and email.
    switch (source) {
        case "login":
            title = t('auth.defaultTitle', { name: user?.name ||"" });
            description = t('auth.loginDescription');
            submit = t('auth.loginSubmit');
            email = user?.email || "";
            break;
        case "signup":
            title = t('auth.defaultTitle', { name: user?.name ||"" });
            description = t('auth.signUpDescription');
            submit = t('auth.signUpSubmit');
            email = user?.email || "";
            break;
        case "forgot":
            title = t('auth.forgotTitle');
            description = t('auth.forgotDescription');
            submit = t('auth.forgotSubmit');
            email = localStorage.getItem('forgotEmail') || "";
            break;
    }
    // If email is not set, redirect to the home page
    // This is to ensure that the user has an email set before proceeding with verification.
    useEffect(() => {
        if (!email) {
            navigate("/");
        }
    }, [email, navigate]);

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
        register("email");
        setValue("email", email || "");
    }, [register, setValue, email]);

    const onChangeOTP = (val: string) => {
        setOtpValue(val);
        setValue("code", val);
    };
    const onSubmit = async (data: EmailVerificationData) => {
        try {
            const languageTag = i18n.language || "en";
            const [language, country = ""] = languageTag.split("-");
            const requestBody: EmailVerificationData = {
                ...data,
                locale: `${language}_${country}`,
            };
            if(source === "forgot") {
                await emailVerification(requestBody);
        
                localStorage.setItem('otpEmail', data.email);
                navigate("/forgot-password/reset-password");
            }else if(source === "signup") {
                await authUser(requestBody);

                toast.success(t('auth.signUpSuccess', { name: user?.name ||"" }));
                navigate("/home");
            }else if(source === "login") {
                await emailVerification(requestBody);

                toast.success(t('auth.loginSuccess', { name: user?.name ||"" }));
                navigate("/home");
            }
        } catch (error) {
            toast.error(t('auth.verificationError'));
        }
    };

    return (
            <Card className="form-card">
                <CardHeader>
                    <CardTitle className="text-2xl">{title}</CardTitle>
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