import '@/styles/utilities.css';

import { Card, CardContent, CardFooter, CardHeader, CardTitle, CardDescription } from "@/app/domains/shared/components/card";
import { Button } from "@/app/domains/shared/components/button.js";
import { InputOTP, InputOTPGroup, InputOTPSlot } from "@/app/domains/shared/components/input-otp.tsx";
import { useTranslation } from 'react-i18next';
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { createEmailVerificationSchema } from "../schemas/emailverification";
import type { EmailVerificationData } from "../schemas/emailverification";
import { emailVerification, authUser } from '../application/authService';
import { useState } from 'react';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useUser } from "@/app/contexts/UserContext";

export function VerificationCodeForm({ source }: { source: "login" | "signup" | "forgot" }) {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const schema = createEmailVerificationSchema(t);
    const { user } = useUser();
    let email = "";
    let title = "";
    let description = "";
    let submit = "";
    //Depending on the source, we set the title, description, submit button text, and email.
    switch (source) {
        case "login":
            title = t('auth.welcomeUser', { name: user?.name ||"" });
            description = t('auth.loginDescription');
            submit = t('auth.loginSubmit');
            email = user?.email || "";
            break;
        case "signup":
            title = t('auth.welcomeUser', { name: user?.name ||"" });
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
        formState: { errors, isSubmitting },
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
            if(source === "forgot") {
                await emailVerification(data);
        
                localStorage.setItem('otpEmail', data.email);
                navigate("/forgot-password/reset-password");
            }else if(source === "signup") {
                await authUser(data);
                console.log("User authenticated successfully");
                navigate("/dashboard");
            }else if(source === "login") {
                await emailVerification(data);
                console.log("Email verification successful");
                navigate("/dashboard");
            }
        } catch (error) {
            console.error("Login error:", error);
        }
    };

    return (
        <div className="form-background">
            <h1 className="card-title">{t('app.title')}</h1>
            <Card className="form-card">
                <CardHeader>
                    <CardTitle className="text-2xl">{title}</CardTitle>
                    <CardDescription className="card-description">{description}</CardDescription>
                    <CardDescription className="card-description">{t('auth.defaultDescription')}</CardDescription>
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
                            {errors.code && <p className="text-red-500 text-sm text-center">{errors.code.message}</p>}
                        </div>
                            <Button type="submit" className="button-green" disabled={isSubmitting}>
                            {submit}
                            </Button>
                    </form>
                </CardContent>
                <CardFooter>
                </CardFooter>
            </Card>
    </div>
    )
}